package com.tamanna.demo.repository;

import com.tamanna.demo.model.Timeslots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<Timeslots, Long>, PagingAndSortingRepository<Timeslots, Long> {

//    @Query(value = "SELECT * FROM Timeslots t JOIN user u ON t.user_id = u.user_id WHERE t.date BETWEEN :startDate AND :endDate AND u.role = :role", nativeQuery = true)
//    List<Timeslots> getAvailableSlotsForCadidate(@Param("dates") HashSet<Date> dates, @Param("role") Integer role);

    @Query(value = "SELECT I.* FROM testdb.timeslots I\n" +
            "JOIN user u on I.user_id = u.user_id \n" +
            "INNER JOIN timeslots t on t.date = I.date\n" +
            "JOIN user cand on t.user_id = cand.user_id \n" +
            "where I.date BETWEEN :startDate AND :endDate and u.role = 1 and cand.user_id = :candidateId and ((t.start_date_time >= I.start_date_time \n" +
            "and t.end_date_time <= I.end_date_time) or (I.start_date_time >= t.start_date_time and I.end_date_time <= t.end_date_time)\n" +
            "or (I.start_date_time BETWEEN t.start_date_time and t.end_date_time or t.start_date_time BETWEEN I.start_date_time and I.end_date_time)\n" +
            "or (I.end_date_time BETWEEN t.start_date_time and t.end_date_time or t.end_date_time BETWEEN I.start_date_time and I.end_date_time)\n" +
            ");", nativeQuery = true)
    List<Timeslots> getAvailableSlotsForCadidate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("candidateId") Long candidateId);

    @Query(value = "SELECT * FROM timeslots WHERE user_id = :userId",nativeQuery = true)
    List<Timeslots> getTimeslotsByUser(@Param("userId") Long userId);

}
