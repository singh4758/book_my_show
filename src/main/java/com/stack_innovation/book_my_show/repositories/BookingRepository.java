package com.stack_innovation.book_my_show.repositories;

import com.stack_innovation.book_my_show.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Override
    Optional<Booking> findById(Long aLong);

    Booking save(Booking booking);
}
