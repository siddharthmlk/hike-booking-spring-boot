package com.element.challenge.hikebooking.controller;

import com.element.challenge.hikebooking.data.dto.TrailDto;
import com.element.challenge.hikebooking.service.HikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/api/v1/hikes"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class HikeController {

    private final HikeService hikeService;


    @Operation(summary = "Get All hikes")
    @ApiResponse(responseCode = "200", description = "Found the trail details", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TrailDto.class))})
    @GetMapping
    public ResponseEntity<List<TrailDto>> getAllHikes(){
        List<TrailDto> trails = hikeService.getAllTrails();
        return ResponseEntity.ok(trails);
    }
}
