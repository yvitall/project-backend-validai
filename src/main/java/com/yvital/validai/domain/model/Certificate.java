package com.yvital.validai.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "certificates")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @Column(name = "issue_date", nullable = false, insertable = false,updatable = false)
    private LocalDate issueDate;

    @Column(name = "certificate_code", nullable = false, unique = true)
    private String certificateCode;
    
    @Column(name = "pdf_url", nullable = false, unique = true)
    private String pdfUrl;
}
