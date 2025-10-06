package org.example.repositories;

import org.example.enums.AccountStatus;
import org.example.enums.AccountType;
import org.example.interfaces.AccountInterface;
import org.example.models.Account;
import org.example.models.Client;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.example.enums.AccountType.CREDIT;

public class AccountRepository extends  BaseRepository implements AccountInterface {

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
                   accounts.add(
                           new Account(rs.getInt("id"), rs.getString("rib"), rs.getBigDecimal("balance"), AccountType.valueOf(rs.getString("account_type")), AccountStatus.valueOf(rs.getString("status"))
                   ));
               }

          return accounts;
       }catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
    }

    public List<Account> getAll(){
        String  query = "select c.id AS client_id," +
                "    c.first_name," +
                "    c.last_name," +
                "    c.salary," +
                "    c.cin," +
                "c.email, a.id as account_id , a.rib , a.balance , a.overdraft_limit , a.currency , a.status ,a.account_type " +
                "   from accounts a " +
                "inner join clients c on c.id = a.client_id ";
        List<Account> accounts = new ArrayList<>();
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setSalary(rs.getBigDecimal("salary"));
                client.setCin(rs.getString("cin"));
                client.setEmail(rs.getString("email"));

                Account account = new Account();
                account.setRib(rs.getString("rib"));
                account.setId(rs.getInt("account_id"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setOverdraftLimit(rs.getBigDecimal("overdraft_limit"));
                account.setCurrency(rs.getString("currency"));
                account.setStatus(AccountStatus.valueOf(rs.getString("status")));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setClient(client);
                accounts.add(account);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return   accounts;

    }

    public boolean close(String rib){
        String query = "update accounts set status = 'PENDING_CLOSURE' where rib = ?";
        try(PreparedStatement stmt = conn().prepareStatement(query)){
             stmt.setString(1,rib);
             int row = stmt.executeUpdate();
            return row > 0;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAccountPendingClosure(){
        String query = "select * from accounts where status = 'PENDING_CLOSURE'";
        List<Account> accounts = new ArrayList<>();
        try(PreparedStatement stmt = conn().prepareStatement(query)){
           ResultSet rs = stmt.executeQuery();
           while(rs.next()){
               Account account = new Account();
               account.setRib(rs.getString("rib"));
               account.setId(rs.getInt("id"));
               account.setBalance(rs.getBigDecimal("balance"));
               account.setOverdraftLimit(rs.getBigDecimal("overdraft_limit"));
               account.setCurrency(rs.getString("currency"));
               account.setStatus(AccountStatus.valueOf(rs.getString("status")));
               account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
               accounts.add(account);
           }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return   accounts;
    }

    public boolean deposit(BigDecimal amount , Account account){
        account.deposit(amount);
        String query = "update accounts set  balance = ? where rib = ?";
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            stmt.setBigDecimal(1,amount);
            stmt.setString(2,account.getRib());
            int row = stmt.executeUpdate();
            return row > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(BigDecimal amount , Account account){
        account.withdraw(amount);
        String query = "update accounts set  balance = ? where rib = ?";
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            stmt.setBigDecimal(1,account.getBalance());
            stmt.setString(2, account.getRib());
            int row = stmt.executeUpdate();
            return row > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Account>  ListAccountByTypeCredit(){
        String query = "select c.id AS client_id,c.first_name,c.last_name,    c.salary, c.cin ,c.email, a.id as account_id" +
                " , a.rib , a.balance , a.overdraft_limit , a.currency , a.status ,a.account_type  from accounts a " +
                " inner join clients c on c.id = a.client_id " +
                "Where a.account_type =  ?";
        List<Account> accounts = new ArrayList<>();
        try(PreparedStatement stmt = conn().prepareStatement(query)){
             PGobject pgTypeAccount = new PGobject();
             pgTypeAccount.setType("account_type");
             pgTypeAccount.setValue(AccountType.CREDIT.name());
             stmt.setObject(1,pgTypeAccount);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setFirstName(rs.getString("first_name"));
                client.setLastName(rs.getString("last_name"));
                client.setSalary(rs.getBigDecimal("salary"));
                client.setCin(rs.getString("cin"));
                client.setEmail(rs.getString("email"));

                Account account = new Account();
                account.setRib(rs.getString("rib"));
                account.setId(rs.getInt("account_id"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setOverdraftLimit(rs.getBigDecimal("overdraft_limit"));
                account.setCurrency(rs.getString("currency"));
                account.setStatus(AccountStatus.valueOf(rs.getString("status")));
                account.setAccountType(AccountType.valueOf(rs.getString("account_type")));
                account.setClient(client);
                accounts.add(account);
            }
                System.out.println(accounts);
        }catch ( SQLException e){
            e.printStackTrace();
        }
        return   accounts;
    }

    public Account getById(int id)  {
        String query  = "select * from accounts  where id = ?";
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt("id"),rs.getString("rib"),rs.getBigDecimal("balance"),
                        AccountType.valueOf(rs.getString("account_type")),
                        AccountStatus.valueOf(rs.getString("status")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean  validateClose(int id){
        String query = "update accounts set  status = 'CLOSED' where id = ?";
        try(PreparedStatement stmt = conn().prepareStatement(query)){
            stmt.setInt(1,id);
            int row = stmt.executeUpdate();
            return row > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }




}
