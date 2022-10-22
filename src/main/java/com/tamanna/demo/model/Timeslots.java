package com.tamanna.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "timeslots")
@Table(name = "timeslots", schema = "testdb")
public class Timeslots implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "start_date_time")
    private LocalTime startDateTime;

    @Column(name = "end_date_time")
    private LocalTime endDateTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
