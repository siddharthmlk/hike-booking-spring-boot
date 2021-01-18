package com.element.challenge.hikebooking.service;

import com.element.challenge.hikebooking.exceptions.InvalidInputDetailsException;
import com.element.challenge.hikebooking.exceptions.RecordNotFoundException;
import com.element.challenge.hikebooking.data.repository.BookingDetailsRepository;
import com.element.challenge.hikebooking.data.dto.BookingDto;
import com.element.challenge.hikebooking.data.dto.BookingIncomingDto;
import com.element.challenge.hikebooking.data.dto.PersonDto;
import com.element.challenge.hikebooking.data.dto.TrailDto;
import com.element.challenge.hikebooking.data.model.BookingDetails;
import com.element.challenge.hikebooking.data.model.BookingStatus;
import com.element.challenge.hikebooking.data.model.HikeDetails;
import com.element.challenge.hikebooking.data.model.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final HikeService hikeService;

    private final BookingDetailsRepository bookingDetailsRepository;


    @Transactional
    public Long createBooking(BookingIncomingDto bookingIncomingDto) {
        Optional<TrailDto> selectedTrailOpt = hikeService.getAllTrails()
                .stream().filter(x -> x.getTrailName().equalsIgnoreCase(bookingIncomingDto.getTrailName())).findFirst();

        validateHikeDetails(bookingIncomingDto.getPersonDetails(), selectedTrailOpt);

        Optional<HikeDetails> hikeEntity = hikeService.getHikeEntityByTrailName(selectedTrailOpt.get().getTrailName());
        BookingDetails bookingEntity = convertToEntity(bookingIncomingDto, hikeEntity.get());

        BookingDetails savedBooking = bookingDetailsRepository.save(bookingEntity);
        return savedBooking.getId();
    }


    public Optional<BookingDto> getBookingDetails(Long bookingId) {
        Optional<BookingDetails> bookingDetails = bookingDetailsRepository.findById(bookingId);
        if (bookingDetails.isPresent()) {
            return Optional.of(convertToDto(bookingDetails.get()));
        } else {
            throw new RecordNotFoundException(String.format("No booking record found for id %d ", bookingId));
        }
    }

    public void deleteBooking(Long bookingId) {
        Optional<BookingDetails> existingBookingOpt = bookingDetailsRepository.findById(bookingId);
        if (existingBookingOpt.isPresent()) {
            BookingDetails existingBooking = existingBookingOpt.get();
            existingBooking.setStatus(BookingStatus.CANCELLED);
            bookingDetailsRepository.save(existingBooking);
        } else {
            throw new RecordNotFoundException(String.format("No booking record found for id %d ", bookingId));
        }
    }

    public List<BookingDto> getAllBookingsByDate(String bookingDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate inputDate = null;
        try {
            inputDate = LocalDate.parse(bookingDate, dtf);
        } catch (DateTimeParseException ex) {
            throw new InvalidInputDetailsException("Allowed format for date is yyyyMMdd");
        }
        List<BookingDetails> bookings = bookingDetailsRepository.findAllByBookingDate(inputDate);

        return bookings.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    private void validateHikeDetails(List<PersonDto> personDetails, Optional<TrailDto> selectedTrailOpt) {
        if (selectedTrailOpt.isPresent()) {
            TrailDto trail = selectedTrailOpt.get();
            String errorMsg = personDetails.stream().filter(x -> !(x.getAge() >= trail.getMinAge() && x.getAge() <= trail.getMaxAge()))
                    .map(x -> "Age criteria not meeting for " + x.getName()).collect(Collectors.joining("\n"));
            if (null != errorMsg && errorMsg.length()>0) {
                System.out.println("error msg"+errorMsg);
                throw new InvalidInputDetailsException(errorMsg);
            }
        } else {
            throw new RecordNotFoundException("Provided trail details not found");
        }
    }


    private BookingDetails convertToEntity(BookingIncomingDto bookingIncomingDto, HikeDetails trail) {
        List<PersonDetails> personDetails = bookingIncomingDto.getPersonDetails().stream().
                map(x -> PersonDetails.builder().name(x.getName()).address(x.getAddress())
                        .age(x.getAge()).mobileNumber(x.getMobileNumber()).email(x.getEmail()).build())
                .collect(Collectors.toList());
        return BookingDetails.builder().bookingDate(bookingIncomingDto.getBookingDate())
                .hikeDetails(trail)
                .status(BookingStatus.BOOKED)
                .totalCost(trail.getPricePerPerson().multiply(new BigDecimal(bookingIncomingDto.getPersonDetails().size())))
                .personDetails(personDetails)
                .build();
    }

    private BookingDto convertToDto(BookingDetails bookingDetails) {
        List<PersonDto> persons = bookingDetails.getPersonDetails().stream().map(x -> PersonDto.builder()
                .name(x.getName()).address(x.getAddress()).age(x.getAge())
                .mobileNumber(x.getMobileNumber()).email(x.getEmail()).build()).collect(Collectors.toList());

        return BookingDto.builder().bookingDate(bookingDetails.getBookingDate()).bookingId(bookingDetails.getId())
                .status(bookingDetails.getStatus().name()).totalPrice(bookingDetails.getTotalCost()).hikerDetails(persons)
                .trailDetails(TrailDto.builder().trailName(bookingDetails.getHikeDetails().getTrailName())
                              .trailStartTime(bookingDetails.getHikeDetails().getTrailStartTime())
                              .trailEndTime(bookingDetails.getHikeDetails().getTrailEndTime())
                              .minAge(bookingDetails.getHikeDetails().getMinAge()).maxAge(bookingDetails.getHikeDetails().getMaxAge())
                              .pricePerPerson(bookingDetails.getHikeDetails().getPricePerPerson()).build())
                .build();
    }
}
