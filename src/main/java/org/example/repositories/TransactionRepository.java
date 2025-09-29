package org.example.repositories;

import org.example.interfaces.TransactionInterface;
import org.example.models.Account;
import org.example.models.Transaction;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.enums.CurrencyType.MAD;
import static org.example.enums.TransactionStatus.SETTLED;
import static org.example.enums.TransactionType.DEPOSIT;
import static org.example.enums.TransactionType.WITHDRAW;

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
        try(PreparedStatement stmt = conn().prepareStatement(query)){
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

}
