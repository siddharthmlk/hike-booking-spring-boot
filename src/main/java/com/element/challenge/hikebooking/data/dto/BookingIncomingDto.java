package com.element.challenge.hikebooking.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingIncomingDto {

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String trailName;

    @JsonProperty(required = true)
    @NotNull
    @FutureOrPresent
    private LocalDate bookingDate;

    @JsonProperty(required = true)
    @NotEmpty
    @Valid
    private List<PersonDto> personDetails;

}
