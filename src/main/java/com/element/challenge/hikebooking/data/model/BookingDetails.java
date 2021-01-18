package com.element.challenge.hikebooking.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booking_details")
public class BookingDetails extends  BaseEntity {

    @Id
    @Column(name = "booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name="booking_status")
    @Enumerated(value = EnumType.STRING)
    private BookingStatus status;

    @Column(name="total_cost", precision = 15, scale = 2)
    private BigDecimal totalCost;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "booking_id")
    private List<PersonDetails> personDetails = new ArrayList<>();

    @ManyToOne(targetEntity = HikeDetails.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "trail_id")
    private HikeDetails hikeDetails;


}
