package com.springboot.bookingrestaurant.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.springboot.bookingrestaurant.entities.Reservation;
import com.springboot.bookingrestaurant.entities.Restaurant;
import com.springboot.bookingrestaurant.entities.Turn;
import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.jsons.CreateReservationRest;
import com.springboot.bookingrestaurant.respositories.ReservationRepository;
import com.springboot.bookingrestaurant.respositories.RestaurantRepository;
import com.springboot.bookingrestaurant.respositories.TurnRepository;
import com.springboot.bookingrestaurant.services.impl.ReservationServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReservationServiceTest {

    private static final Date DATE = new Date();
    private static final Long PERSON = 1L;
    private static final Long RESTAURANT_ID = 1L;
    private static final Long TURN_ID = 1L;
    private static final String LOCATOR = "Burger 2";
    private static final String RESERVATION_DELETED = "LOCATOR_DELETED";

    private static final String NAME = "Burger";
    private static final String DESCRIPTION = "Todo tipo de hamburguesas";
    private static final String ADDRESS = "Av. Galilea, 1234";
    private static final String IMAGE = "www.image-test.com";

    private static final String TURN_NAME = "Burger";

    CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
    private static final Restaurant RESTAURANT = new Restaurant();
    private static final Optional<Restaurant> OPTIONAL_RESTAURANT = Optional.of(RESTAURANT);
    private static final Optional<Restaurant> OPTIONAL_RESTAURANT_EMPTY = Optional.empty();

    private static final Reservation RESERVATION = new Reservation();
    private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION);
    private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY = Optional.empty();

    private static final Turn TURN = new Turn();
    private static final Optional<Turn> OPTIONAL_TURN = Optional.of(TURN);
    private static final Optional<Turn> OPTIONAL_TURN_EMPTY = Optional.empty();

    private static final List<Turn> TURN_LIST = new ArrayList<>();

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TurnRepository turnRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Before
    public void init() throws BookingException {
        MockitoAnnotations.initMocks(this);

        RESTAURANT.setName(NAME);
        RESTAURANT.setDescription(DESCRIPTION);
        RESTAURANT.setAddress(ADDRESS);
        RESTAURANT.setId(RESTAURANT_ID);
        RESTAURANT.setImage(IMAGE);
        RESTAURANT.setTurns(TURN_LIST);

        TURN.setId(TURN_ID);
        TURN.setName(TURN_NAME);
        TURN.setRestaurant(RESTAURANT);

        RESERVATION.setDate(DATE);
        RESERVATION.setPerson(PERSON);
        RESERVATION.setRestaurant(RESTAURANT);
        RESERVATION.setLocator(LOCATOR);
        RESERVATION.setTurn(TURN.getName());

        CREATE_RESERVATION_REST.setDate(DATE);
        CREATE_RESERVATION_REST.setPerson(PERSON);
        CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
        CREATE_RESERVATION_REST.setTurnId(TURN_ID);
    }

    @Test
    public void createReservationTest() throws BookingException {
        Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
        Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
        Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
                .thenReturn(OPTIONAL_RESERVATION_EMPTY);

        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());
        reservationService.createReservation(CREATE_RESERVATION_REST);
    }

    @Test(expected = BookingException.class)
    public void createReservationFindByIdTestError() throws BookingException {
        Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT_EMPTY);

        reservationService.createReservation(CREATE_RESERVATION_REST);
        fail();
    }

    @Test(expected = BookingException.class)
    public void createReservationTurnFindByIdError() throws BookingException {
        Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
        Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN_EMPTY);

        reservationService.createReservation(CREATE_RESERVATION_REST);
        fail();
    }

    @Test(expected = BookingException.class)
    public void createReservationTurnAndRestaurantTestError() throws BookingException {
        Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
        Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN_EMPTY);
        Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
                .thenReturn(OPTIONAL_RESERVATION);

        reservationService.createReservation(CREATE_RESERVATION_REST);
        fail();
    }

    @Test(expected = BookingException.class)
    public void createReservationInternalServerErrorTest() throws BookingException {
        Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
        Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
        Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
                .thenReturn(OPTIONAL_RESERVATION_EMPTY);

        Mockito.doThrow(Exception.class).when(reservationRepository).save(Mockito.any(Reservation.class));
        reservationService.createReservation(CREATE_RESERVATION_REST);
        fail();
    }

    @Test
    public void deleteReservationTest() throws BookingException {
        Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
        Mockito.when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);

        final String response = reservationService.deleteReservation(LOCATOR);

        assertEquals(response, RESERVATION_DELETED);
    }

    @Test(expected = BookingException.class)
    public void deleteReservationNotFoundErrorTest() throws BookingException {
        Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION_EMPTY);
        Mockito.when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);

        final String response = reservationService.deleteReservation(LOCATOR);
        assertEquals(response, RESERVATION_DELETED);
        fail();
    }

    @Test(expected = BookingException.class)
    public void deleteReservationInternalServerErrorTest() throws BookingException {
        Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
        Mockito.doThrow(Exception.class).when(reservationRepository).deleteByLocator(LOCATOR);

        final String response = reservationService.deleteReservation(LOCATOR);
        assertEquals(response, RESERVATION_DELETED);
        fail();
    }
}
