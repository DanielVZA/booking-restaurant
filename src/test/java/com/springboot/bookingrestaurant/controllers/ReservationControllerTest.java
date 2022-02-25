package com.springboot.bookingrestaurant.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.jsons.CreateReservationRest;
import com.springboot.bookingrestaurant.jsons.ReservationRest;
import com.springboot.bookingrestaurant.responses.BookingResponse;
import com.springboot.bookingrestaurant.services.IReservationService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReservationControllerTest {

    private static final Long RESERVATION_ID = 1L;
    private static final String SUCCESS_STATUS = "Success";
    private static final String SUCCESS_CODE = "200 OK";
    private static final String OK = "OK";
    private static final String RESERVATION_DELETED = "LOCATOR_DELETED";

    CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
    private static final ReservationRest RESERVATION_REST = new ReservationRest();

    private static final Long RESTAURANT_ID = 1L;
    private static final Date DATE = new Date();
    private static final Long PERSON = 1L;
    private static final Long TURN_ID = 1L;
    private static final String LOCATOR = "BURGER 2";

    @Mock
    IReservationService reservationService;

    @InjectMocks
    ReservationController reservationController;

    @Before
    public void init() throws BookingException {
        MockitoAnnotations.initMocks(this);

        CREATE_RESERVATION_REST.setDate(DATE);
        CREATE_RESERVATION_REST.setPerson(PERSON);
        CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
        CREATE_RESERVATION_REST.setTurnId(TURN_ID);

        RESERVATION_REST.setPerson(PERSON);
        RESERVATION_REST.setDate(DATE);
        RESERVATION_REST.setLocator(LOCATOR);
        RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
        RESERVATION_REST.setTurnId(TURN_ID);

        Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR);
        Mockito.when(reservationService.getReservationById(RESERVATION_ID)).thenReturn(RESERVATION_REST);
        Mockito.when(reservationService.deleteReservation(LOCATOR)).thenReturn(RESERVATION_DELETED);
    }

    @Test
    public void createReservationTest() throws BookingException {
        final BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);

        assertEquals(response.getStatus(), SUCCESS_STATUS);
        assertEquals(response.getCode(), SUCCESS_CODE);
        assertEquals(response.getMessage(), OK);
        assertEquals(response.getData(), LOCATOR);
    }

    @Test
    public void getReservationByIdTest() throws BookingException {
        final BookingResponse<ReservationRest> response = reservationController.getReservationById(RESERVATION_ID);

        assertEquals(response.getStatus(), SUCCESS_STATUS);
        assertEquals(response.getCode(), SUCCESS_CODE);
        assertEquals(response.getMessage(), OK);
        assertEquals(response.getData(), RESERVATION_REST);
    }

    @Test
    public void deleteReservationTest() throws BookingException {
        final BookingResponse<String> response = reservationController.deleteReservation(LOCATOR);

        assertEquals(response.getStatus(), SUCCESS_STATUS);
        assertEquals(response.getCode(), SUCCESS_CODE);
        assertEquals(response.getMessage(), OK);
        assertEquals(response.getData(), RESERVATION_DELETED);
    }

}
