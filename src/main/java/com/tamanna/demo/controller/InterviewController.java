package com.tamanna.demo.controller;

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

    @GetMapping(value = "tamannam/schedual")
    @ResponseBody
    public Object getUserSchedual(@RequestParam(value = "userId", required = false) String userId){
        return userId;
    }

    @PostMapping(value = "tamannam/schedual")
    @ResponseBody
    public Object addUserSchedual(@RequestBody InterviewerRequest request) throws ValidationException{

        User user = userService.get(request.getId());
        if(user.getRole().equals(request.getRole())){
            List<Timeslots> timeslots = Util.prepareTimeSlotList(request.getAvailability(), user);
            List<Timeslots> savedTimeslots = timeSlotService.addAll(timeslots);
            if(savedTimeslots.size() == timeslots.size()){
                return new Response<Object>(200L, "Time slots saved successfully", null);
            } else{
                return new Response<Object>(500L, "Time slots are not saved", null);
            }
        } else {
            throw new ValidationException("User is not valid for this operation");
        }
    }
}
