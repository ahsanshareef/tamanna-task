package com.tamanna.demo.controller;

import com.tamanna.demo.enums.Role;
import com.tamanna.demo.model.Timeslots;
import com.tamanna.demo.model.User;
import com.tamanna.demo.request.InterviewerRequest;
import com.tamanna.demo.response.Response;
import com.tamanna.demo.service.ITimeSlotService;
import com.tamanna.demo.service.IUserService;
import com.tamanna.demo.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
public class InterviewController {

    @Autowired
    IUserService userService;

    @Autowired
    ITimeSlotService timeSlotService;

    @PostMapping(value = "tamannam/schedual")
    @ResponseBody
    public Object addUserSchedual(@RequestBody InterviewerRequest request){

        User user = userService.get(request.getId());
        if(user.getRole().equals(request.getRole())){
            List<Timeslots> timeslots = null;
            try {
                timeslots = Util.prepareTimeSlotList(request.getAvailability(), user);
                if(request.getRole().equals(Role.CANDIDATE)){
                    return null;
                } else {
                    List<Timeslots> savedTimeslots = timeSlotService.addInterviewerTimeSlots(timeslots);
                    if(savedTimeslots.size() == timeslots.size()){
                        return new Response<Object>(200L, "Time slots saved successfully", savedTimeslots);
                    } else{
                        return new Response<>(500L, "Time slots are not saved", null);
                    }
                }
            } catch (ValidationException e) {
                return new Response<>(500L, e.getMessage(), null);
            }
        } else {
            return new Response<>(500L, "User is not valid for this operation", null);
        }
    }
}
