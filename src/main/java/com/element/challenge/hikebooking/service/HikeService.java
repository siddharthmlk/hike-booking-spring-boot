package com.element.challenge.hikebooking.service;

import com.element.challenge.hikebooking.data.repository.HikeDetailsRepository;
import com.element.challenge.hikebooking.data.dto.TrailDto;
import com.element.challenge.hikebooking.data.model.HikeDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HikeService {

    private final HikeDetailsRepository hikeDetailsRepository;


    @Cacheable(cacheNames = "trails", key = "#root.methodName")
    public List<TrailDto> getAllTrails(){
        return hikeDetailsRepository.findAll().stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    Optional<HikeDetails> getHikeEntityByTrailName(String trailName){
        return hikeDetailsRepository.findByTrailName(trailName);
    }

    private TrailDto convertToDto(HikeDetails hikeDetails) {
        return TrailDto.builder().trailName(hikeDetails.getTrailName()).trailStartTime(hikeDetails.getTrailStartTime())
                .trailEndTime(hikeDetails.getTrailEndTime()).minAge(hikeDetails.getMinAge()).maxAge(hikeDetails.getMaxAge())
                .pricePerPerson(hikeDetails.getPricePerPerson()).build();
    }
}
