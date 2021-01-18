package com.element.challenge.hikebooking.data.repository;

import com.element.challenge.hikebooking.data.model.HikeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HikeDetailsRepository extends JpaRepository<HikeDetails, Long> {

   Optional<HikeDetails> findByTrailName(String trailName);
}
