package com.springboot.bookingrestaurant.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.springboot.bookingrestaurant.entities.Board;
import com.springboot.bookingrestaurant.entities.Reservation;
import com.springboot.bookingrestaurant.entities.Restaurant;
import com.springboot.bookingrestaurant.entities.Turn;
import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.jsons.RestaurantRest;
import com.springboot.bookingrestaurant.respositories.RestaurantRepository;
import com.springboot.bookingrestaurant.services.impl.RestaurantServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RestaurantServiceTest {

    private static final Long RESTAURANT_ID = 1L;
    private static final String NAME = "Burger";
    private static final String DESCRIPTION = "Todo tipo de hamburguesas";
    private static final String ADDRESS = "Av. Galilea, 1234";
    private static final String IMAGE = "www.image-test.com";

    public static final Restaurant RESTAURANT = new Restaurant();
    public static final List<Turn> TURN_LIST = new ArrayList<>();
    public static final List<Board> BOARD_LIST = new ArrayList<>();
    public static final List<Reservation> RESERVATION_LIST = new ArrayList<>();

    @Mock
    RestaurantRepository restaurantRepository;

    @InjectMocks
    RestaurantServiceImpl restaurantService;

    @Before
    public void init() throws BookingException {
        MockitoAnnotations.initMocks(this);

        RESTAURANT.setName(NAME);
        RESTAURANT.setDescription(DESCRIPTION);
        RESTAURANT.setAddress(ADDRESS);
        RESTAURANT.setImage(IMAGE);
        RESTAURANT.setTurns(TURN_LIST);
        RESTAURANT.setBoards(BOARD_LIST);
        RESTAURANT.setReservations(RESERVATION_LIST);
    }

    @Test
    public void getRestaurantByIdTest() throws BookingException {
        Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));

        restaurantService.getRestaurantById(RESTAURANT_ID);
    }

    @Test(expected = BookingException.class)
    public void getRestaurantByIdTestError() throws BookingException {
        Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());
        restaurantService.getRestaurantById(RESTAURANT_ID);
        fail();
    }

    @Test
    public void getRestaurantsTest() throws BookingException {
        final Restaurant restaurant = new Restaurant();
        Mockito.when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant));
        final List<RestaurantRest> response = restaurantService.getRestaurants();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(response.size(), 1);
    }
}
