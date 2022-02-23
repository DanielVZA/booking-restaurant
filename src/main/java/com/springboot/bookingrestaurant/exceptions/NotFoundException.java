package com.springboot.bookingrestaurant.exceptions;

import java.util.Arrays;

import com.springboot.bookingrestaurant.dtos.ErrorDto;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BookingException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String code, String message) {
        super(code, HttpStatus.NOT_FOUND.value(), message);
    }

    public NotFoundException(String message, String code, int responseCode, ErrorDto data) {
        super(code, HttpStatus.NOT_FOUND.value(), message, Arrays.asList(data));
    }
}
