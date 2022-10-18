package com.tamanna.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "timeslots")
@Table(name = "timeslots", schema = "testdb")
public class Timeslots {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "start_date_time")
    private LocalTime startDateTime;

    @Column(name = "end_date_time")
    private LocalTime endDateTime;

    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private User user;
}
