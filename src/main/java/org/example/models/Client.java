package org.example.models;

import org.example.enums.ClientStatus;

import java.math.BigDecimal;
import java.util.Objects;

public class Client   {

    private  int id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  BigDecimal salary;
    private  String address;
    private  String cin;
    private ClientStatus status;


    public Client(int id, String firstName, String lastName, String email, BigDecimal salary, String address, String cin ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.address = address;
        this.cin = cin;
        this.status = ClientStatus.ACTIVE;
    }

    public Client() {

    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
//        return "Client{" +
            return "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", address='" + address + '\'' +
                ", cin='" + cin + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getCin() {
        return cin;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id == client.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
