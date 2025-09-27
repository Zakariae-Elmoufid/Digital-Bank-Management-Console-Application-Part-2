package org.example.repositories;

import org.example.interfaces.ClientInterface;
import org.example.models.Client;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class ClientRepository  extends BaseRepository implements ClientInterface {

   public Client create(String firstName, String lastName, String email, BigDecimal salary, String address, String cin){
       String query = "insert into clients (first_name , last_name , email,salary, address, cin )" +
               "VALUES (?, ?, ?, ?, ?, ?) ";
       try(PreparedStatement stmt = conn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1,firstName);
            stmt.setString(2,lastName);
            stmt.setString(3,email);
            stmt.setBigDecimal(4,salary);
            stmt.setString(5,address);
            stmt.setString(6,cin);

           int rows = stmt.executeUpdate();
           if (rows > 0) {
               ResultSet rs = stmt.getGeneratedKeys();
               if (rs.next()) {
                   int lastId = rs.getInt(1);
                   System.out.println("Last inserted ID: " + lastId);
                   return new Client(lastId, firstName, lastName, email, salary, address, cin);
               }
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
   }
   public List<Client>  getAll(){
       String query = "select * from clients";
       List<Client> clients = new ArrayList<>();
       try(Statement  stmt = conn().createStatement();
           ResultSet rs = stmt.executeQuery(query)){
           while (rs.next()){
               clients.add(new Client(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("email"),rs.getBigDecimal("salary"),rs.getString("address"),rs.getString("cin")));
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return clients;
   }

   public boolean update(int id , Map<String, Object> data){


    StringBuilder query = new StringBuilder("update clients set ");
    List<Object> values = new ArrayList<>();

    for(Map.Entry<String, Object> entry : data.entrySet()){
        query.append(entry.getKey()).append(" = ?, ");
        values.add(entry.getValue());
    }
     query.delete(query.length()-2, query.length());
     query.append(" where id=?");
     values.add(id);

     try(PreparedStatement stmt = conn().prepareStatement(query.toString())){
         for(int i = 0; i < values.size(); i++){
             stmt.setObject( i+1, values.get(i));
         }

         int row  = stmt.executeUpdate();
         if (row > 0) {
             return true;
         }
     }catch (SQLException e){
         e.printStackTrace();
     }
         return false;
    }

   public boolean close(int id){
       String query = "update clients set status = 'PENDING_CLOSURE' where id=?";
       try(PreparedStatement stmt = conn().prepareStatement(query)){
           stmt.setInt(1,id);
           int row  = stmt.executeUpdate();
          return row > 0  ;
       }catch (SQLException e){
           e.printStackTrace();
       }
       return false;
   }

}
