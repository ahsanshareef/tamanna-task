package com.tamanna.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotsForCandidateResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String interviewerName;
    private String date;
    private String timeSlots;

}
