package com.element.challenge.hikebooking.data.repository;

import com.element.challenge.hikebooking.data.model.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {

    //@Query("select b from BookingDetails b where b.bookingDate = :bookingDate")
    List<BookingDetails> findAllByBookingDate(LocalDate bookingDate);
}
