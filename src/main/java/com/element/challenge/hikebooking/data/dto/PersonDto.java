package com.element.challenge.hikebooking.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDto {

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String name;

    @JsonProperty(required = true)
    @NotNull
    @Positive
    private Integer age;

    @JsonProperty(required = true)
    @NotNull
    @Positive
    private Integer mobileNumber;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String address;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    private String email;


}
