package com.springboot.bookingrestaurant.services;

import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.jsons.CreateReservationRest;
import com.springboot.bookingrestaurant.jsons.ReservationRest;

public interface IReservationService {

    ReservationRest getReservationById(Long reservationId) throws BookingException;

    String createReservation(CreateReservationRest createReservationRest) throws BookingException;

    public String deleteReservation(String locator) throws BookingException;
}
