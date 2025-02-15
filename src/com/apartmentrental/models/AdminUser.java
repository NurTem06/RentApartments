package com.apartmentrental.models;

public class AdminUser extends User {
    public AdminUser(int id, String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance) {
        super(id, firstName, lastName, username, phoneNumber, password, walletBalance, Role.ADMIN);
    }

    @Override
    public boolean canPerformAdminActions() {
        return true;
    }

    @Override
    public boolean canPerformManagerActions() {
        return true;
    }
}
