package com.stack_innovation.book_my_show.controllers;

import com.stack_innovation.book_my_show.dtos.BookMovieRequestDTO;
import com.stack_innovation.book_my_show.dtos.BookMovieResponseDTO;
import com.stack_innovation.book_my_show.dtos.ResponseStatus;
import com.stack_innovation.book_my_show.models.Booking;
import com.stack_innovation.book_my_show.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    BookingService bookingService;
    @Autowired
    public BookingController(
            BookingService bookingService
    ) {
        this.bookingService = bookingService;
    }
    public BookMovieResponseDTO bookMovie(BookMovieRequestDTO request) {
        BookMovieResponseDTO bookMovieResponseDTO = new BookMovieResponseDTO();
        Booking booking;

        try{
            booking = bookingService.bookMovie(
                    request.getUserId(),
                    request.getSeatIds(),
                    request.getShowId()
            );

            bookMovieResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            bookMovieResponseDTO.setAmount(booking.getAmount());
            bookMovieResponseDTO.setBookingId(booking.getId());
        } catch(RuntimeException e) {
            bookMovieResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }

        return bookMovieResponseDTO;
    }
}
