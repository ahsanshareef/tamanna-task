package com.tamanna.demo.service;

import com.tamanna.demo.model.Timeslots;

import java.util.List;

public interface ITimeSlotService {
    List<Timeslots> addInterviewerTimeSlots(List<Timeslots> timeslots);

    List<Timeslots> addCandidateTimeSlots(List<Timeslots> timeslots);
}
