package com.apartmentrental.models;

public class RegularUser extends User {
    public RegularUser(int id, String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance) {
        super(id, firstName, lastName, username, phoneNumber, password, walletBalance, Role.USER);
    }

    @Override
    public boolean canPerformAdminActions() {
        return false;
    }

    @Override
    public boolean canPerformManagerActions() {
        return false;
    }
}
