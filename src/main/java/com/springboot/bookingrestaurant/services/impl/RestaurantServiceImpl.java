package com.springboot.bookingrestaurant.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.bookingrestaurant.entities.Restaurant;
import com.springboot.bookingrestaurant.exceptions.BookingException;
import com.springboot.bookingrestaurant.exceptions.NotFoundException;
import com.springboot.bookingrestaurant.jsons.RestaurantRest;
import com.springboot.bookingrestaurant.respositories.RestaurantRepository;
import com.springboot.bookingrestaurant.services.IRestaurantService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements IRestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public RestaurantRest getRestaurantById(Long restaurantId) throws BookingException {
        return modelMapper.map(getRestaurantEntity(restaurantId), RestaurantRest.class);
    }

    @Override
    public List<RestaurantRest> getRestaurants() throws BookingException {
        final List<Restaurant> restaurantsEntity = restaurantRepository.findAll();
        return restaurantsEntity.stream().map(service -> modelMapper.map(service, RestaurantRest.class))
                .collect(Collectors.toList());
    }

    private Restaurant getRestaurantEntity(Long restaurantId) throws BookingException {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("SNOT-404-1", "RESTAURANT_NOT_FOUND"));
    }
}
