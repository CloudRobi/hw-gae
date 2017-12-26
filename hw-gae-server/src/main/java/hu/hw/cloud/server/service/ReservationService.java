/**
 * 
 */
package hu.hw.cloud.server.service;

import java.util.Date;
import java.util.List;

import hu.hw.cloud.server.entity.reservation.Reservation;
import hu.hw.cloud.shared.dto.reservation.ReservationDto;

/**
 * @author CR
 *
 */
public interface ReservationService extends CrudService<Reservation, ReservationDto> {
	List<Reservation> getReservationsByRoom(String roomKey, Date fromDate, Date toDate);

	List<Reservation> getReservationsByArrivalDate(Date fromDate, Date toDate);
}
