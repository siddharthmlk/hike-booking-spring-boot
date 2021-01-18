package com.element.challenge.hikebooking.controller;

import com.element.challenge.hikebooking.data.dto.BookingIncomingDto;
import com.element.challenge.hikebooking.data.dto.PersonDto;
import com.element.challenge.hikebooking.data.model.BookingDetails;
import com.element.challenge.hikebooking.data.repository.BookingDetailsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;



import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;


    @Test
    public void testBookingCreationAndValidation() throws Exception {
     BookingIncomingDto request = BookingIncomingDto.builder().bookingDate(LocalDate.now()).trailName("Gondor").personDetails(Arrays.asList(PersonDto.builder().
                name("sidd").address("add").age(20).email("abc@abc.com").mobileNumber(1234).build())).build();

     mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings").contentType("application/json")
     .content(objectMapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().isCreated());

        List<BookingDetails> entities = bookingDetailsRepository.findAll();
        assertEquals(1,entities.size());
        assertEquals("Gondor",entities.get(0).getHikeDetails().getTrailName());

    }


    @Test
    public void testBookingDeletionAndValidation() throws Exception {
        BookingIncomingDto request = BookingIncomingDto.builder().bookingDate(LocalDate.now()).trailName("Gondor").personDetails(Arrays.asList(PersonDto.builder().
                name("sidd").address("add").age(20).email("abc@abc.com").mobileNumber(1234).build())).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings").contentType("application/json")
                .content(objectMapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().isCreated());

        request.setTrailName("Shire");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings").contentType("application/json")
                .content(objectMapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().isCreated());

        List<BookingDetails> entities = bookingDetailsRepository.findAll();
        assertEquals(2,entities.size());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/bookings/{bookingId}", "2")
                .contentType("application/json").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bookings/{bookingId}", "2")
                .contentType("application/json").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("CANCELLED")))
                .andDo(MockMvcResultHandlers.print());


    }
}
