package com.apartmentrental.repositories.interfaces;
import com.apartmentrental.models.Rental;
import java.util.List;

public interface IRentalRepository {
    void rentApartment(int userId, int apartmentId, String startDate, String durationType);
    List<Rental> getRentalsByUserId(int userId);
}