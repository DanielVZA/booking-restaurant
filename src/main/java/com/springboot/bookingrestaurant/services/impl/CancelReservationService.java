package com.springboot.bookingrestaurant.services.impl;

import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.exceptions.InternalServerErrorException;
import com.springboot.bookingrestaurant.exceptions.NotFoundException;
import com.springboot.bookingrestaurant.respositories.ReservationRepository;
import com.springboot.bookingrestaurant.services.ICancelReservationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelReservationService implements ICancelReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public String deleteReservation(String locator) throws BookingException {

        reservationRepository.findByLocator(locator)
                .orElseThrow(() -> new NotFoundException("LOCATOR_NOT_FOUND", "LOCATOR_NOT_FOUND"));

        try {
            reservationRepository.deleteByLocator(locator);
        } catch (Exception e) {
            LOGGER.error("INTERNAL_SERVER_ERROR", e);
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }

        return null;
    }

}
