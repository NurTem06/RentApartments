package com.apartmentrental.models;

public class FullRentalDescription {
    private int rentalId;
    private int userId;
    private String userName;
    private int apartmentId;
    private String apartmentName;
    private String startDate;
    private String endDate;

    public FullRentalDescription(int rentalId, int userId, String userName, int apartmentId, String apartmentName, String startDate, String endDate) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.userName = userName;
        this.apartmentId = apartmentId;
        this.apartmentName = apartmentName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "FullRentalDescription{" +
                "rentalId=" + rentalId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", apartmentId=" + apartmentId +
                ", apartmentName='" + apartmentName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }
}