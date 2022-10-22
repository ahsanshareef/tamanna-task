package com.tamanna.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedualDateTimeDTO {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date availableDate;

    private List<String> availableTime;
}
