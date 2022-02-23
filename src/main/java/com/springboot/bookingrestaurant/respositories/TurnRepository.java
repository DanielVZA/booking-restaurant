package com.springboot.bookingrestaurant.respositories;

import java.util.Optional;

import com.springboot.bookingrestaurant.entities.Turn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {

    Optional<Turn> findById(Long id);
}
