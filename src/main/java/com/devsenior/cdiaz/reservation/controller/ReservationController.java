package com.devsenior.cdiaz.reservation.controller;

import com.devsenior.cdiaz.reservation.entity.Reservation;
import com.devsenior.cdiaz.reservation.exception.BusinessRuleException;
import com.devsenior.cdiaz.reservation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@GetMapping
	public ResponseEntity<List<Reservation>> getAllReservations() {
		return ResponseEntity.ok(reservationService.listReservations());
	}

	@PostMapping
	public ResponseEntity<?> createReservation(@RequestBody Reservation request) {
		try {
			Reservation reservation = reservationService.createReservation(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
		} catch (BusinessRuleException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
		try {
			reservationService.cancelReservation(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessRuleException ex) {
			HttpStatus status = ex.getMessage().contains("No existe")
					? HttpStatus.NOT_FOUND
					: HttpStatus.CONFLICT;
			return ResponseEntity.status(status).body(ex.getMessage());
		}
	}

}
