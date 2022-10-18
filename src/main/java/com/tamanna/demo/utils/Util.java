package com.tamanna.demo.utils;

import com.tamanna.demo.dto.SchedualDateTimeDTO;
import com.tamanna.demo.model.Timeslots;
import com.tamanna.demo.model.User;

import javax.xml.bind.ValidationException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static Boolean validateTime(LocalTime startTime, LocalTime endTime){
        if(startTime.getMinute() > 0 || endTime.getMinute() > 0){
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    public static List<Timeslots> prepareTimeSlotList(List<SchedualDateTimeDTO> availablityList, User user) throws ValidationException{
        List<Timeslots> timeslots = new ArrayList<>();
        for(SchedualDateTimeDTO obj : availablityList){
            for(String time : obj.getAvailableTime()){
                String[] timeRange = time.replace(" ","").split("-");
                if(timeRange.length == 2){
                    LocalTime startTime = LocalTime.parse(timeRange[0]);
                    LocalTime endTime = LocalTime.parse(timeRange[1]);
                    if(Boolean.TRUE == Util.validateTime(startTime, endTime)){
                        timeslots.add(new Timeslots(0l, obj.getAvailableDate(), startTime, endTime, user));
                    } else {
                        throw new ValidationException("Selected time is not correct");
                    }
                }else {
                    throw new ValidationException("Selected time is not correct");
                }
            }
        }
        return timeslots;
    }

}
