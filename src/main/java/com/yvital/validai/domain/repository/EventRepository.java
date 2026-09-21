package com.yvital.validai.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yvital.validai.domain.enums.StatusEvent;
import com.yvital.validai.domain.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
    List<Event> findByStatus(StatusEvent status);
}