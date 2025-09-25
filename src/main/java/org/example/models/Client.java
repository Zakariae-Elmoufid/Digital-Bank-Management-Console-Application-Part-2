package org.example.models;

import java.math.BigDecimal;

public class Client   {

    private  int id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  BigDecimal salary;
    private  String address;
    private  String cin;

    public Client(int id, String firstName, String lastName, String email, BigDecimal salary, String address, String cin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.address = address;
        this.cin = cin;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", address='" + address + '\'' +
                ", cin='" + cin + '\'' +
                '}';
    }
}
