package org.example;

import org.example.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLOutput;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
          Connection conn = DatabaseConnection.getConnection();
          if(conn!=null){
              System.out.println("Connection successful!");
          }
    }
}