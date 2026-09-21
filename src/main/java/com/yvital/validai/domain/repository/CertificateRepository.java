package com.yvital.validai.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yvital.validai.domain.model.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long>{

}
