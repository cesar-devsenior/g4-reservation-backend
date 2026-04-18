package com.devsenior.cdiaz.reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cdiaz.reservation.entity.Reservation;
import com.devsenior.cdiaz.reservation.entity.ReservationStatus;
import com.devsenior.cdiaz.reservation.exception.BusinessRuleException;
import com.devsenior.cdiaz.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ReservationService {

	private final ReservationRepository reservationRepository;

	@Transactional(readOnly = true)
	public List<Reservation> listReservations() {
		return reservationRepository.findAll();
	}

	public Reservation createReservation(Reservation reservation) {
		if (reservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime())) {
			throw new BusinessRuleException("Ya existe una reserva activa para la misma fecha y hora.");
		}

		reservation.setStatus(ReservationStatus.ACTIVE);
		return reservationRepository.save(reservation);
	}

	public void cancelReservation(Long id) {
		Reservation reservation = reservationRepository.findById(id)
				.orElseThrow(() -> new BusinessRuleException("No existe ninguna reserva con el id indicado."));

		if (reservation.getStatus() == ReservationStatus.CANCELLED) {
			throw new BusinessRuleException("La reserva ya se encuentra cancelada.");
		}

		reservation.setStatus(ReservationStatus.CANCELLED);
		reservationRepository.save(reservation);
	}
}
