package com.tamanna.demo.service.impl;

import com.tamanna.demo.model.Timeslots;
import com.tamanna.demo.repository.TimeSlotRepository;
import com.tamanna.demo.response.TimeSlotsForCandidateResponse;
import com.tamanna.demo.service.ITimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotServiceImpl implements ITimeSlotService {

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Override
    public List<Timeslots> addAll(List<Timeslots> timeslots) {
        return timeSlotRepository.saveAll(timeslots);
    }

    @Override
    public List<TimeSlotsForCandidateResponse> getAvailableTimeSlotsForCandiDate(Long candidateId, Date startDate, Date endDate) {

        List<Timeslots> candidateTimeslots = timeSlotRepository.getTimeslotsByUser(candidateId);
        List<Timeslots> availableSlotsForCadidate = timeSlotRepository.getAvailableSlotsForCadidate(startDate, endDate, candidateId);
        List<TimeSlotsForCandidateResponse> result = new ArrayList<>();
        Boolean flag = Boolean.FALSE;
        for(Timeslots interviewer : availableSlotsForCadidate){
            for(Timeslots candidate : candidateTimeslots){
                if(interviewer.getStartDateTime().equals(candidate.getStartDateTime()) && (Math.abs(interviewer.getStartDateTime().getHour() - candidate.getEndDateTime().getHour()) >= 1)){
                    flag = Boolean.TRUE;
                } else if(interviewer.getStartDateTime().isAfter(candidate.getStartDateTime()) && interviewer.getStartDateTime().isBefore(candidate.getEndDateTime())
                        && (Math.abs(interviewer.getStartDateTime().getHour() - candidate.getEndDateTime().getHour()) >= 1)){
                    flag = Boolean.TRUE;
                } else if(candidate.getStartDateTime().isAfter(interviewer.getStartDateTime()) && candidate.getStartDateTime().isBefore(interviewer.getEndDateTime())
                        && (Math.abs(candidate.getStartDateTime().getHour() - interviewer.getEndDateTime().getHour()) >= 1)){
                    flag = Boolean.TRUE;
                }

                if(Boolean.TRUE == flag){
                    TimeSlotsForCandidateResponse object = new TimeSlotsForCandidateResponse(interviewer.getUser().getFirstName() + " " + interviewer.getUser().getLastName(),
                            interviewer.getDate().toString(),interviewer.getStartDateTime() + " - " + interviewer.getStartDateTime().plusHours(1L));
                    if(!result.contains(object)){
                        result.add(object);
                    }

                }
            }
        }
        return result;
    }

}
