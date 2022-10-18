package com.tamanna.demo.repository;

import com.tamanna.demo.model.Timeslots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<Timeslots, Long> {
}
