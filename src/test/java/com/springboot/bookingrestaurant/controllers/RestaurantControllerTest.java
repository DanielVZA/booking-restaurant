package com.springboot.bookingrestaurant.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.jsons.RestaurantRest;
import com.springboot.bookingrestaurant.jsons.TurnRest;
import com.springboot.bookingrestaurant.responses.BookingResponse;
import com.springboot.bookingrestaurant.services.IRestaurantService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RestaurantControllerTest {

    private static final Long RESTAURANT_ID = 1L;
    private static final String SUCCESS_STATUS = "Success";
    private static final String SUCCESS_CODE = "200 OK";
    private static final String OK = "OK";

    private static final String NAME = "Burger";
    private static final String DESCRIPTION = "Todo tipo de hamburguesas";
    private static final String ADDRESS = "Av. Galilea, 1234";
    private static final String IMAGE = "www.image-test.com";

    public static final RestaurantRest RESTAURANT_REST = new RestaurantRest();
    public static final List<TurnRest> TURN_LIST = new ArrayList<>();
    public static final List<RestaurantRest> RESTAURANT_LIST = new ArrayList<>();

    @Mock
    IRestaurantService restaurantService;

    @InjectMocks
    RestaurantController restaurantController;

    @Before
    public void init() throws BookingException {
        MockitoAnnotations.initMocks(this);

        RESTAURANT_REST.setName(NAME);
        RESTAURANT_REST.setDescription(DESCRIPTION);
        RESTAURANT_REST.setAddress(ADDRESS);
        RESTAURANT_REST.setImage(IMAGE);
        RESTAURANT_REST.setTurns(TURN_LIST);

        Mockito.when(restaurantService.getRestaurantById(RESTAURANT_ID)).thenReturn(RESTAURANT_REST);
    }

    @Test
    public void getRestaurantByIdTest() throws BookingException {
        final BookingResponse<RestaurantRest> response = restaurantController.getRestaurantById(RESTAURANT_ID);

        assertEquals(response.getStatus(), SUCCESS_STATUS);
        assertEquals(response.getCode(), SUCCESS_CODE);
        assertEquals(response.getMessage(), OK);
        assertEquals(response.getData(), RESTAURANT_REST);
    }

    @Test
    public void getRestaurantsTest() throws BookingException {
        final BookingResponse<List<RestaurantRest>> response = restaurantController.getRestaurants();

        assertEquals(response.getStatus(), SUCCESS_STATUS);
        assertEquals(response.getCode(), SUCCESS_CODE);
        assertEquals(response.getMessage(), OK);
        assertEquals(response.getData(), RESTAURANT_LIST);
    }
}
