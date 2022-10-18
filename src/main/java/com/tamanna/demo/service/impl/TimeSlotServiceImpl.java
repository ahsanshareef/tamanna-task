package com.tamanna.demo.service.impl;

import com.tamanna.demo.model.Timeslots;
import com.tamanna.demo.repository.TimeSlotRepository;
import com.tamanna.demo.service.ITimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotServiceImpl implements ITimeSlotService {

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Override
    public List<Timeslots> addAll(List<Timeslots> timeslots) {
        return timeSlotRepository.saveAll(timeslots);
    }
}
