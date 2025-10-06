package org.example.repositories;

import org.example.interfaces.UserInterface;
import  org.example.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.example.util.Session;

public class UserRepository extends BaseRepository implements UserInterface {
    public User findUser(String email,String  password){
       String query = "SELECT * FROM users WHERE email = ? AND password = ?";
       try (PreparedStatement stmt = conn().prepareStatement(query)) {
           stmt.setString(1, email);
           stmt.setString(2, password);
           try (ResultSet rs = stmt.executeQuery()) {
               if(rs.next()){
                   User user = new User(
                           rs.getInt("id"),
                           rs.getString("username"),
                           rs.getString("email"),
                           rs.getString("password"),
                           rs.getBoolean("is_active"),
                           rs.getInt("role_id")
                   );
                   return user;
               }
           }
       } catch (SQLException e){
           e.printStackTrace();
       }
  return null ;
   }

}
