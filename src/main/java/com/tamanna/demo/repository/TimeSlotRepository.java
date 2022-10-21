package com.tamanna.demo.repository;

import com.tamanna.demo.model.Timeslots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<Timeslots, Long> {

    @Query(value = "FROM Timeslots WHERE date IN :dates")
    List<Timeslots> getAvailableSlotsForCadidate(@Param("dates") List<Date> dates);

}
