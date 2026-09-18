package com.yvital.validai.domain.model;

import java.time.LocalDateTime;

import com.yvital.validai.domain.enums.StatusRegistrations;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "registrations", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "event_id" }))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne 
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "registration_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime registrationAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusRegistrations status;

    @Column(name = "attendance_confirmed", nullable = false)
    private boolean attendanceConfirmed;

    @Column(name = "attendance_confirmed_at")
    private LocalDateTime attendanceConfirmedAt;

    @Column(name = "qr_code", nullable = false, unique = true)
    private String qrCode;
}