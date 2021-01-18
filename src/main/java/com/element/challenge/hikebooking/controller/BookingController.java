package com.element.challenge.hikebooking.controller;

import com.element.challenge.hikebooking.data.dto.BookingDto;
import com.element.challenge.hikebooking.data.dto.BookingIncomingDto;
import com.element.challenge.hikebooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/api/v1/bookings"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Create a new hike booking")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Hike booking is created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Booking request is not valid", content = @Content)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createBooking(@Valid @RequestBody BookingIncomingDto bookingIncomingDto){
    Long bookingId = bookingService.createBooking(bookingIncomingDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{bookingId}")
                .buildAndExpand(bookingId).toUri();
    return ResponseEntity.created(location).build();
    }


    @Operation(summary = "Get booking details by booking Id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the booking details", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookingDto.class))}),
    @ApiResponse(responseCode = "404", description = "Booking Id not found", content = @Content)})
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingDetails(@PathVariable("bookingId") Long bookingId){
        final Optional<BookingDto> booking = bookingService.getBookingDetails(bookingId);
        if(!booking.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(booking.get());
    }


    @Operation(summary = "Delete a booking by Id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Delete request is successful", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Booking Id not found", content = @Content)})
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<BookingDto> deleteBooking(@PathVariable("bookingId") Long bookingId){
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Get all booking details by date")
    @ApiResponse(responseCode = "200", description = "Found the booking details", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookingDto.class))})
    @GetMapping("/report/{bookingDate}")
    public ResponseEntity<List<BookingDto>> getAllBookingsForInputDate( @Parameter(description = "booking date in yyyyMMdd format (e.g. 20201030)") @PathVariable("bookingDate") String bookingDate){
        List<BookingDto> bookings = bookingService.getAllBookingsByDate(bookingDate);
        return ResponseEntity.ok(bookings);
    }

}
