package com.element.challenge.hikebooking.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.element.challenge.hikebooking.data.dto.BookingIncomingDto;
import com.element.challenge.hikebooking.data.dto.PersonDto;
import com.element.challenge.hikebooking.data.dto.TrailDto;
import com.element.challenge.hikebooking.data.model.BookingDetails;
import com.element.challenge.hikebooking.data.model.HikeDetails;
import com.element.challenge.hikebooking.data.repository.BookingDetailsRepository;
import com.element.challenge.hikebooking.exceptions.InvalidInputDetailsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private HikeService hikeService;

    @Mock
    private BookingDetailsRepository repository;

    private BookingService bookingService;

    @BeforeEach
    public void setup(){
        bookingService = new BookingService(hikeService,repository);
        when(hikeService.getAllTrails()).thenReturn(Arrays.asList(TrailDto.builder().
                trailName("abc").pricePerPerson(new BigDecimal(20)).minAge(20).maxAge(50).build()));
    }

    @Test
    public void testCreateBooking(){
        BookingIncomingDto request = BookingIncomingDto.builder().bookingDate(LocalDate.now()).trailName("abc").personDetails(Arrays.asList(PersonDto.builder().
                name("sidd").address("add").age(20).email("abc@abc.com").mobileNumber(1234).build())).build();
        when(hikeService.getHikeEntityByTrailName(any())).thenReturn(Optional.of(HikeDetails.builder().pricePerPerson(new BigDecimal(5)).build()));
        when(repository.save(any())).thenReturn(BookingDetails.builder().build());

        bookingService.createBooking(request);
        verify(hikeService).getAllTrails();
        verify(repository).save(Mockito.any());
    }


    @Test
    public void testExceptionCreateBooking(){
        BookingIncomingDto request = BookingIncomingDto.builder().bookingDate(LocalDate.now()).trailName("abc").personDetails(Arrays.asList(PersonDto.builder().
                name("sidd").address("add").age(15).email("abc@abc.com").mobileNumber(1234).build())).build();
        InvalidInputDetailsException exception = assertThrows(InvalidInputDetailsException.class,
                () -> bookingService.createBooking(request), "No exception thrown");

        assertTrue(exception.getMessage().contains("criteria"));
    }


}
