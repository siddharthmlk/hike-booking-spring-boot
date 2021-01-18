package com.element.challenge.hikebooking.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hike_details")
public class HikeDetails extends  BaseEntity {

    @Id
    @Column(name = "trail_id")
    private Long id;

    @Column(name = "trail_name")
    private String trailName;

    @Column(name = "start_time")
    private LocalTime trailStartTime;

    @Column(name = "end_time")
    private LocalTime trailEndTime;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "price_per_person", precision = 10, scale = 2)
    private BigDecimal pricePerPerson;

}
