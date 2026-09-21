package com.yvital.validai.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yvital.validai.domain.model.Event;
import com.yvital.validai.domain.enums.StatusEvent;

public interface EventRepository extends JpaRepository<Event, Long>{
    Optional<Event> findByStatus(StatusEvent status);
}
