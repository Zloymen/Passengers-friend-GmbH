package com.passengers.friend.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "aenaflight_source")
@Data
public class DestinationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 8)
    private String adep;

    @Column(length = 8)
    private String ades;

    @Column(length = 8, name = "flight_code", nullable = false)
    private String flightCode;

    @Column(length = 8, name = "flight_number", nullable = false)
    private String flightNumber;

    @Column(length = 8, name = "carrier_code")
    private String carrierCode;

    @Column(length = 8, name = "carrier_number")
    private String carrierNumber;

    @Column(length = 256, name = "status_info")
    private String statusInfo;

    @Column(name = "schd_dep_lt", nullable = false)
    private LocalDateTime schdDepLt;

    @Column(name = "schd_arr_lt", nullable = false)
    private LocalDateTime schdArrLt;

    @Column(name = "est_dep_lt")
    private LocalDateTime estDepLt;

    @Column(name = "est_arr_lt")
    private LocalDateTime estArrLt;

    @Column(name = "act_dep_lt")
    private LocalDateTime actDepLt;

    @Column(name = "act_arr_lt")
    private LocalDateTime actArrLt;

    @Column(name = "flt_leg_seq_no", nullable = false)
    private Integer seqNo;

    @Column(name = "aircraft_name_scheduled")
    private String aircraftNameScheduled;

    @Column(name = "baggage_info", length = 128)
    private String baggageInfo;

    @Column(name = "counter", length = 128)
    private String counter;

    @Column(name = "gate_info", length = 128)
    private String gateInfo;

    @Column(name = "lounge_info", length = 128)
    private String loungeInfo;

    @Column(name = "terminal_info", length = 128)
    private String terminalInfo;

    @Column(name = "arr_terminal_info", length = 128)
    private String arrTerminalInfo;

    @Column(name = "source_data")
    private String sourceData;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public boolean isFullData(){
        return !(Objects.isNull(adep)
                || Objects.isNull(ades)
                || Objects.isNull(flightCode)
                || Objects.isNull(flightNumber)
                || Objects.isNull(carrierCode)
                || Objects.isNull(carrierNumber)
                || Objects.isNull(statusInfo)
                || Objects.isNull(schdDepLt)
                || Objects.isNull(schdArrLt)
                || Objects.isNull(estDepLt)
                || Objects.isNull(actDepLt)
                || Objects.isNull(seqNo)
                || Objects.isNull(aircraftNameScheduled)
                || Objects.isNull(sourceData)
                || Objects.isNull(createdAt));
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
