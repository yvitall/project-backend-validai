package com.yvital.validai.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.yvital.validai.domain.enums.StatusEvent;

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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(name = "title", nullable = false)
    @Size(min = 5)
    private String title;

    @Column(name = "description", nullable = false)
    @Size(min = 10)
    private String description;

    @Column(name = "location", nullable = false)
    @Size(min = 10)
    private String location;

    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Column(name = "hour", nullable = false)
    private LocalTime hour;
    
    @Column(name = "workload", nullable = false)
    private Integer workload;
    
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
    
    @Column(name = "speaker_name", nullable = false)
    @Size(min = 3)
    private String speakerName;
    
    @Column(name = "speaker_title", nullable = false)
    @Size(min = 3)
    private String speakerTitle;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusEvent status;
}
