package com.springboot.bookingrestaurant.controllers;

import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.jsons.CreateReservationRest;
import com.springboot.bookingrestaurant.jsons.ReservationRest;
import com.springboot.bookingrestaurant.responses.BookingResponse;
import com.springboot.bookingrestaurant.services.IReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/booking-restaurant" + "v1")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "reservation/{reservationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponse<ReservationRest> getReservationById(@PathVariable Long reservationId)
            throws BookingException {
        return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                reservationService.getReservationById(reservationId));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "reservation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponse<String> createReservation(@RequestBody CreateReservationRest createReservationRest)
            throws BookingException {
        return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                reservationService.createReservation(createReservationRest));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "reservation/{locator}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponse<String> deleteReservation(@RequestParam String locator) throws BookingException {
        return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                reservationService.deleteReservation(locator));
    }
}
