package com.element.challenge.hikebooking.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrailDto {

    @JsonProperty
    private String trailName;

    @JsonProperty
    private LocalTime trailStartTime;

    @JsonProperty
    private LocalTime trailEndTime;

    @JsonProperty
    private Integer minAge;

    @JsonProperty
    private Integer maxAge;

    @JsonProperty
    @JsonSerialize(using = CurrencyAppendSerializer.class)
    private BigDecimal pricePerPerson;
}
