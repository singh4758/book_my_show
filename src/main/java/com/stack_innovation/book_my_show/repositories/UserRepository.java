package com.stack_innovation.book_my_show.repositories;

import com.stack_innovation.book_my_show.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

// In place of component we can also put @Component but for readable we put @Repository
// that tell the spring boot that this component and for user who reding this code
// understand that that the componenet is repository component

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long aLong);
}
