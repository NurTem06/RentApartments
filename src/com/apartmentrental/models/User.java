package com.apartmentrental.models;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String password;
    private double walletBalance;

    public User() {}

    public User(String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance) {
        setFirstName(firstName);
        setLastName(lastName);
        setUsername(username);
        setPhoneNumber(phoneNumber);
        setPassword(password);
        setWalletBalance(walletBalance);
    }

    public User(int id, String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance) {
        this(firstName, lastName, username, phoneNumber, password, walletBalance);
        setId(id);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getWalletBalance() { return walletBalance; }
    public void setWalletBalance(double walletBalance) { this.walletBalance = walletBalance; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", walletBalance=" + walletBalance +
                '}';
    }
}
