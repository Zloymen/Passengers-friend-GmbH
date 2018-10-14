package com.passengers.friend.data.entity;

import lombok.Data;

/**
 * id bigint primary key, unique identifier
 * act_arr_date_time_lt character varying(64) actual arrival timestamp
 * aircraft_name_scheduled text scheduled aircraft name
 * arr_apt_name_es character varying(128) Arrival airport name in Spanish
 * arr_apt_code_iata character varying(8) IATA/ICAO code of arrival airport
 * baggage_info character varying(128) Baggage information of flight
 * carrier_airline_name_en character varying(128) Carrier airline name English
 * carrier_icao_code character varying(8) Carrier IATA/ICAO code
 * carrier_number character varying(8) Carrier number
 * counter character varying(64) Registration counter
 * dep_apt_name_es character varying(128) Departure airport name Spanish
 * dep_apt_code_iata character varying(8) IATA/ICAO code of departure airport
 * est_arr_date_time_lt character varying(64) estimated arrival timestamp
 * est_dep_date_time_lt character varying(64) estimated departure timestamp
 * flight_airline_name_en character varying(128) Flight airline name English
 * flight_airline_name character varying(128) Flight Airline name
 * flight_icao_code character varying(8) IATA/ICAO flight airline code
 * flight_number character varying(8) flight number
 * flt_leg_seq_no character varying(8) flight leg sequence id
 * gate_info character varying(128) gate information
 * lounge_info character varying(128) lounge information
 * schd_arr_only_date_lt character varying(32) scheduled arrival date
 * schd_arr_only_time_lt character varying(32) scheduled arrival time
 * source_data text source of data
 * status_info character varying(128) flight status
 * terminal_info character varying(128) terminal information
 * arr_terminal_info character varying(128) arrival terminal information
 * act_dep_date_time_lt character varying(64) actual departure timestamp
 * schd_dep_only_date_lt character varying(32) scheduled departure date
 * schd_dep_only_time_lt character varying(32) scheduled departure time
 * created_at bigint unix timestamp when record was created
 */


@Data
public class SourcesData {

    private String actArrDateTimeLt;
    private String aircraftNameScheduled;
    private String arrAptNameEspano;
    private String arrAptCodeIata;
    private String baggageInfo;
    private String carrierAirlineNameEnglish;
    private String carrierIcaoCode;
    private String carrierNumber;
    private String counter;
    private String depAptNameEspano;
    private String depAptCodeIata;
    private String estArrDateTimeLt;
    private String estDepDateTimeLt;
    private String flightAirlineNameEnglish;
    private String flightAirlineName;
    private String flightIcaoCode;
    private String flightNumber;
    private String fltLegSeqNo;
    private String gateInfo;
    private String loungeInfo;
    private String schdArrOnlyDateLt;
    private String schdArrOnlyTimeLt;
    private String sourceData;
    private String statusInfo;
    private String terminalInfo;
    private String arrTerminalInfo;
    private String actDepDateTimeLt;
    private String schdDepOnlyDateLt;
    private String schdDepOnlyTimeLt;
    private Long createdAt;
}
