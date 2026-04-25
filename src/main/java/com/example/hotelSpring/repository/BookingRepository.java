package com.example.hotelSpring.repository;

import com.example.hotelSpring.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByClientId(Long clientId);

    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByStatus(String status);

// найти активные бронирования (текущая дата между датами заезда и выезда)
@Query("SELECT b FROM Booking b WHERE b.checkInDate <= :date AND b.checkOutDate >= :date AND b.status = 'Подтверждено'")
List<Booking> findByActiveBooking(@Param("date")LocalDate date);


}
