package com.apartmentrental.models;

public class ManagerUser extends User {
    public ManagerUser(int id, String firstName, String lastName, String username, String phoneNumber, String password, double walletBalance) {
        super(id, firstName, lastName, username, phoneNumber, password, walletBalance, Role.MANAGER);
    }

    @Override
    public boolean canPerformAdminActions() {
        return false;
    }

    @Override
    public boolean canPerformManagerActions() {
        return true;
    }
}
