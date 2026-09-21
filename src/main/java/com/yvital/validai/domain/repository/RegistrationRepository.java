package com.yvital.validai.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yvital.validai.domain.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}
