package com.stack_innovation.book_my_show.services;

import com.stack_innovation.book_my_show.models.*;
import com.stack_innovation.book_my_show.repositories.BookingRepository;
import com.stack_innovation.book_my_show.repositories.ShowRepository;
import com.stack_innovation.book_my_show.repositories.ShowSeatRepository;
import com.stack_innovation.book_my_show.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

@Service
public class BookingService {

    UserRepository userRepository;
    ShowRepository showRepository;
    PriceCalculatorService priceCalculatorService;
    BookingRepository bookingRepository;
    ShowSeatRepository showSeatRepository;

    @Autowired
    public BookingService(
            UserRepository userRepository,
            ShowRepository showRepository,
            PriceCalculatorService priceCalculatorService,
            BookingRepository bookingRepository,
            ShowSeatRepository showSeatRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.priceCalculatorService = priceCalculatorService;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> seatIds, Long showId) {
        // 1. get the user with userId

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new RuntimeException();
        }

        User bookedBy = user.get();

        // 2. Get the show with that showId

        Optional<Show> showOptional = showRepository.findById(userId);

        if(showOptional.isEmpty()){
            throw new RuntimeException();
        }

        Show show = showOptional.get();

        // -------------------- START TRANSACTION -------------------

        // 3. Get the seat with that seats id

        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatIds);

        // 4. check if your seats are available
        for (ShowSeat showSeat : showSeats) {
            if(
                    !showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)
                    || (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED)
                    && Duration.between(
                            showSeat.getBlockedAt().toInstant(),
                            new Date().toInstant()).toMinutes() > 15)
            ) {
                throw new RuntimeException();
            }
        }

        // 5. if no return error

        // 6. If yes, mark the status of seat as locked
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat: showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        // 7. save the updated seat status in DB

        // -------------------- END TRANSACTION ----------------------

        // 8. return transaction.
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShowSeats(savedShowSeats);
        booking.setUser(bookedBy);
        booking.setBookedAt(new Date());
        booking.setShow(show);
        booking.setAmount(priceCalculatorService.calculatePrice(showSeats, show));
        bookingRepository.save(booking);

        return booking;
    }
}
