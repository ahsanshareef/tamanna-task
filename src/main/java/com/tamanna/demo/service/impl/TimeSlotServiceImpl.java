package com.tamanna.demo.service.impl;

import com.sun.xml.bind.v2.runtime.reflect.ListTransducedAccessorImpl;
import com.tamanna.demo.model.Timeslots;
import com.tamanna.demo.repository.TimeSlotRepository;
import com.tamanna.demo.service.ITimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotServiceImpl implements ITimeSlotService {

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Override
    public List<Timeslots> addInterviewerTimeSlots(List<Timeslots> timeslots) {
        return timeSlotRepository.saveAll(timeslots);
    }

    @Override
    public List<Timeslots> addCandidateTimeSlots(List<Timeslots> timeslots) {
        List<Timeslots> savedTimeslots = timeSlotRepository.saveAll(timeslots);
        List<Timeslots> availableSlotsForCadidate = new ArrayList<>();
        if(savedTimeslots.size() == timeslots.size()){
            List<Date> dates = new ArrayList<>();
            timeslots.stream().forEach(obj -> {
                dates.add(obj.getDate());
            });
            availableSlotsForCadidate = timeSlotRepository.getAvailableSlotsForCadidate(dates);

        }
        return availableSlotsForCadidate;
    }

}
