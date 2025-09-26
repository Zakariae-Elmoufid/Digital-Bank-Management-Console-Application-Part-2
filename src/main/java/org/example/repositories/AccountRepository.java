package org.example.repositories;

import org.example.enums.AccountType;
import org.example.models.Account;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class AccountRepository extends  BaseRepository{


    public int create(int client_id,Account account){
       String query = "insert into accounts (client_id ,rib,balance,account_type) values (?,?,?,?)";

       try(PreparedStatement stmt = conn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
           stmt.setInt(1,client_id);
           stmt.setString(2, account.getRib());
           stmt.setBigDecimal(3,account.getBalance());

           PGobject accountTypeObj = new PGobject();
           accountTypeObj.setType("account_type");
           accountTypeObj.setValue(account.getAccountType().name()); // "SAVINGS", "CURRENT", "CREDIT"
           stmt.setObject(4, accountTypeObj);

           int rows = stmt.executeUpdate();
           if(rows>0){
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

    public List<Account> getAccountByClientId(int client_id){
       String  query = "select * from accounts where client_id = ?";
       List<Account> accounts = new ArrayList<>();
       try(PreparedStatement stmt =  conn().prepareStatement(query)){
           stmt.setInt(1,client_id);

          ResultSet rs  = stmt.executeQuery();
               while (rs.next()){
                   accounts.add(new Account(
                           rs.getInt("id"),
                           rs.getString("rib"),
                           rs.getBigDecimal("balance"),
                           rs.getBigDecimal("overdraft_limit"),
                           AccountType.valueOf(rs.getString("account_type")),
                           rs.getBoolean("is_active"),
                           rs.getString("created_at")
                   ));               }

          return accounts;
       }catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
    }


}
