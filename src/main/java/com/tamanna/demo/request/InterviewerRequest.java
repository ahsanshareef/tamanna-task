package com.tamanna.demo.request;

import com.tamanna.demo.dto.SchedualDateTimeDTO;
import com.tamanna.demo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewerRequest {
    private List<SchedualDateTimeDTO> availability;
    private Long id;
    private Role role;
}
