package com.springboot.bookingrestaurant.exceptions;

import java.util.Arrays;

import com.springboot.bookingrestaurant.dtos.ErrorDto;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BookingException {

    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(String code, String message) {
        super(code, HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public InternalServerErrorException(String message, String code, int responseCode, ErrorDto data) {
        super(code, HttpStatus.INTERNAL_SERVER_ERROR.value(), message, Arrays.asList(data));
    }
}
