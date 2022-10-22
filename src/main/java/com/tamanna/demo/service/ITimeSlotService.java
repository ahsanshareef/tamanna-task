package com.tamanna.demo.service;

import com.tamanna.demo.model.Timeslots;
import com.tamanna.demo.response.TimeSlotsForCandidateResponse;

import java.util.Date;
import java.util.List;

public interface ITimeSlotService {
    List<Timeslots> addAll(List<Timeslots> timeslots);

    List<TimeSlotsForCandidateResponse> getAvailableTimeSlotsForCandiDate(Long candidateId, Date startDate, Date endDate);
}
