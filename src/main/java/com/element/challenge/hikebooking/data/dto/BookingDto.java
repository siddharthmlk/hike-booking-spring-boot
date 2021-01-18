package com.element.challenge.hikebooking.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingDto {

    @JsonProperty
    private Long bookingId;

    @JsonProperty
    private String status;

    @JsonProperty
    private LocalDate bookingDate;

    @JsonProperty
    private TrailDto trailDetails;

    @JsonProperty
    @JsonSerialize(using = CurrencyAppendSerializer.class)
    private BigDecimal totalPrice;

    @JsonProperty
    private List<PersonDto> hikerDetails;
}
