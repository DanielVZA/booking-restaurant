package com.springboot.bookingrestaurant.services.impl;

import com.springboot.bookingrestaurant.entities.Reservation;
import com.springboot.bookingrestaurant.entities.Restaurant;
import com.springboot.bookingrestaurant.entities.Turn;
import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.exceptions.InternalServerErrorException;
import com.springboot.bookingrestaurant.exceptions.NotFoundException;
import com.springboot.bookingrestaurant.jsons.CreateReservationRest;
import com.springboot.bookingrestaurant.jsons.ReservationRest;
import com.springboot.bookingrestaurant.respositories.ReservationRepository;
import com.springboot.bookingrestaurant.respositories.RestaurantRepository;
import com.springboot.bookingrestaurant.respositories.TurnRepository;
import com.springboot.bookingrestaurant.services.IReservationService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements IReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private TurnRepository turnRepository;

    public static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ReservationRest getReservation(Long reservationId) throws BookingException {
        return modelMapper.map(getReservationEntity(reservationId), ReservationRest.class);
    }

    @Override
    public String createReservation(final CreateReservationRest createReservationRest) throws BookingException {
        final Restaurant restaurantId = restaurantRepository.findById(createReservationRest.getRestaurantId())
                .orElseThrow(() -> new NotFoundException("RESTAURANT_NOT_FOUND", "RESTAURANT_NOT_FOUND"));

        final Turn turnId = turnRepository.findById(createReservationRest.getTurnId())
                .orElseThrow(() -> new NotFoundException("TURN_NOT_FOUND", "TURN_NOT_FOUND"));

        String locator = generateLocator(restaurantId, createReservationRest);

        final Reservation reservation = new Reservation();
        reservation.setLocator(generateLocator(restaurantId, createReservationRest));
        reservation.setPerson(createReservationRest.getPerson());
        reservation.setDate(createReservationRest.getDate());
        reservation.setRestaurant(restaurantId);
        reservation.setTurn(turnId.getName());

        try {
            reservationRepository.save(reservation);
        } catch (final Exception e) {
            LOGGER.error("INTERNAL_SERVER_ERROR", e);
            throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
        }

        return locator;
    }

    private String generateLocator(Restaurant restaurantId, CreateReservationRest createReservationRest)
            throws BookingException {

        return restaurantId.getName() + createReservationRest.getTurnId();
    }

    private Reservation getReservationEntity(Long reservationId) throws BookingException {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("SNOT-404-1", "RESERVATION_NOT_FOUND"));
    }
}
