package com.devsenior.cdiaz.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsenior.cdiaz.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	boolean existsByDateAndTime(LocalDate date, LocalTime time);

}
