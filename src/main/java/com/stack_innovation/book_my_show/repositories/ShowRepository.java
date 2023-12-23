package com.stack_innovation.book_my_show.repositories;

import com.stack_innovation.book_my_show.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    Optional<Show> findById(Long aLong);
}
