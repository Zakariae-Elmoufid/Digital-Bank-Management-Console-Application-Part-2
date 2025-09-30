package org.example.repositories;

import org.example.enums.SourceType;
import org.example.interfaces.TransactionInterface;
import org.example.models.Account;
import org.example.models.BankRevenue;
import org.example.models.Transaction;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.enums.CurrencyType.MAD;
import static org.example.enums.TransactionStatus.SETTLED;
import static org.example.enums.TransactionType.*;

public class TransactionRepository extends BaseRepository implements TransactionInterface {

    public int deposit(BigDecimal amount, Account account){
       Transaction transaction  = new Transaction(DEPOSIT,amount,null,account,MAD,SETTLED,"deposit");
       String query = "Insert into transactions (amount,currency,transfer_in_id,type,status) values (?,?,?,?,?)";
       try(PreparedStatement stmt = conn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

           stmt.setBigDecimal(1,amount);
           PGobject currencyObj = new PGobject();
           currencyObj.setType("currency_type");
           currencyObj.setValue(transaction.getCurrency().name());

           stmt.setObject(2, currencyObj);

           stmt.setInt(3,account.getId());

           PGobject typeObj = new PGobject();
           typeObj.setType("transaction_type");
           typeObj.setValue(transaction.getType().name());
           stmt.setObject(4,typeObj);

           PGobject statusObj = new PGobject();
           statusObj.setType("transaction_status");
           statusObj.setValue(transaction.getStatus().name());
           stmt.setObject(5,statusObj);

           int row = stmt.executeUpdate();
           if(row>0){
               ResultSet rs = stmt.getGeneratedKeys();
               if(rs.next()){
                   return rs.getInt(1);
               }
           }
       }catch (SQLException e){
           e.printStackTrace();

       }
       return -1;
    }

    public int withdraw(BigDecimal amount, Account account){
        Transaction transaction  = new Transaction(WITHDRAW,amount,account,null,MAD,SETTLED,"withdraw ");
        String query = "Insert into transactions (amount,currency,transfer_out_id,type,status) values (?,?,?,?,?)";
        try(PreparedStatement stmt = conn().prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
           stmt.setBigDecimal(1,amount);

           PGobject currencyObj = new PGobject();
           currencyObj.setType("currency_type");
           currencyObj.setValue(transaction.getCurrency().name());
           stmt.setObject(2,currencyObj);

           stmt.setInt(3,account.getId());

           PGobject typeObj = new PGobject();
           typeObj.setType("transaction_type");
           typeObj.setValue(transaction.getType().name());
           stmt.setObject(4,typeObj);

           PGobject statusObj = new PGobject();
           statusObj.setType("transaction_status");
           statusObj.setValue(transaction.getStatus().name());
           stmt.setObject(5,statusObj);
            int row = stmt.executeUpdate();
            if(row>0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
      }

    public int transferInter(BigDecimal amount, Account fromAccount, Account toAccount) {
        Transaction transaction = new Transaction(INTERNAL_TRANSFER,amount,fromAccount,toAccount,MAD,SETTLED,"transfer_inter");
        String query =  "insert into transactions (amount,transfer_out_id,transfer_in_id, type, currency,status) values (?,?,?,?,?,?)";

        try(PreparedStatement stmt  = conn().prepareStatement(query ,Statement.RETURN_GENERATED_KEYS )){
            stmt.setBigDecimal(1,amount);
            stmt.setInt(2,fromAccount.getId());
            stmt.setInt(3,toAccount.getId());

            PGobject typeObj = new PGobject();
            typeObj.setType("transaction_type");
            typeObj.setValue(transaction.getType().name());
            stmt.setObject(4,typeObj);

            PGobject currencyObj = new PGobject();
            currencyObj.setType("currency_type");
            currencyObj.setValue(transaction.getCurrency().name());
            stmt.setObject(5,currencyObj);

            PGobject statusObj = new PGobject();
            statusObj.setType("transaction_status");
            statusObj.setValue(transaction.getStatus().name());
            stmt.setObject(6,statusObj);

            int row = stmt.executeUpdate();
            if(row>0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public int transferExternal(Account fromAccount, Account toAccount ,BigDecimal amount,BigDecimal feeAmount, BigDecimal amountPlusFee,String description,int feeRuleId) {
        Transaction transaction = new Transaction(EXTERNAL_TRANSFER,amount,fromAccount,toAccount,MAD,SETTLED,description);
        String query = "insert into  transactions (amount,transfer_out_id,transfer_in_id,type,currency, fee_rule_id, fee_amount ,status , description) values (?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setBigDecimal(1,amount);
            stmt.setInt(2,fromAccount.getId());
            stmt.setInt(3,toAccount.getId());

            PGobject typeObj = new PGobject();
            typeObj.setType("transaction_type");
            typeObj.setValue(transaction.getType().name());
            stmt.setObject(4,typeObj);

            PGobject statusObj = new PGobject();
            statusObj.setType("transaction_status");
            statusObj.setValue(transaction.getStatus().name());
            stmt.setObject(8,statusObj);

            PGobject currencyObj = new PGobject();
            currencyObj.setType("currency_type");
            currencyObj.setValue(transaction.getCurrency().name());
            stmt.setObject(5,currencyObj);

            stmt.setInt(6,feeRuleId);
            stmt.setBigDecimal(7,feeAmount);
            stmt.setString(9,description);
            int row = stmt.executeUpdate();
            if(row>0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public int AddRevenueTransaction(SourceType type, BigDecimal feeAmount, int transaction_id)  {
         String query = "insert into banke_revenue(source_type , revenue, transaction_id) values (?,?,?)";
        BankRevenue bankRevenue = new BankRevenue(null, String.valueOf(type),feeAmount,MAD);
         try(PreparedStatement stmt = conn().prepareStatement(query , Statement.RETURN_GENERATED_KEYS)){
             PGobject typeObj = new PGobject();
             typeObj.setType("source_type");
             typeObj.setValue(type.name());
             stmt.setObject(1,typeObj);
             stmt.setBigDecimal(2,feeAmount);
             stmt.setInt(3,transaction_id);
             int row = stmt.executeUpdate();
             if(row>0){
                 ResultSet rs = stmt.getGeneratedKeys();
                 if(rs.next()){
                    int  id =rs.getInt(1);
                    bankRevenue.setId(id);
                    return id;
                 }
             }

         }catch (SQLException e){
            e.printStackTrace();
         }
        return -1;
    }

}
