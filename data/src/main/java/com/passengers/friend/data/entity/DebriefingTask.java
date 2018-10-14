package com.passengers.friend.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "debriefing_task")
@NoArgsConstructor
@Data
public class DebriefingTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private LocalDate day;

    @Column(name = "flight_icao_code")
    private String icao;

    @Column(name = "flight_number")
    private String number;

    @Column(name = "work_type")
    @Enumerated
    private EWorkType type = EWorkType.NEW;

    public DebriefingTask(LocalDate day, String icao, String number){
        this.day = day;
        this.icao = icao;
        this.number = number;
    }
}
