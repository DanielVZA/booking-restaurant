package com.springboot.bookingrestaurant.services;

import com.springboot.bookingrestaurant.exceptions.BookingException;

public interface ICancelReservationService {

    public String deleteReservation(String locator) throws BookingException;
}