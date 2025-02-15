package com.apartmentrental.models;

public abstract class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String phoneNumber;
    protected String password;
    protected double walletBalance;
    protected Role role;

    protected User() {}

    // Базовый конструктор, который устанавливает все поля, кроме id и role
    protected User(String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance) {
        setFirstName(firstName);
        setLastName(lastName);
        setUsername(username);
        setPhoneNumber(phoneNumber);
        setPassword(password);
        setWalletBalance(walletBalance);
    }

    // Конструктор, который вызывает базовый и устанавливает id
    protected User(int id, String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance) {
        this(firstName, lastName, username, phoneNumber, password, walletBalance);
        setId(id);
    }

    // Конструктор, который вызывает базовый и устанавливает role
    protected User(String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance, Role role) {
        this(firstName, lastName, username, phoneNumber, password, walletBalance);
        setRole(role);
    }

    // Конструктор, который вызывает конструктор с id и затем устанавливает role
    protected User(int id, String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance, Role role) {
        this(id, firstName, lastName, username, phoneNumber, password, walletBalance);
        setRole(role);
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

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public abstract boolean canPerformAdminActions();
    public abstract boolean canPerformManagerActions();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", walletBalance=" + walletBalance +
                ", role=" + role +
                '}';
    }
}