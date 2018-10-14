package com.passengers.friend.data.mapper;

import com.passengers.friend.data.entity.SourcesData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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

public class SourcesRowMapper implements RowMapper<SourcesData> {

    @Override
    public SourcesData mapRow(ResultSet resultSet, int i) throws SQLException {
        SourcesData result = new SourcesData();
        result.setActArrDateTimeLt(resultSet.getString("act_arr_date_time_lt"));
        result.setAircraftNameScheduled(resultSet.getString("aircraft_name_scheduled"));
        result.setArrAptNameEspano(resultSet.getString("arr_apt_name_es"));
        result.setArrAptCodeIata(resultSet.getString("arr_apt_code_iata"));
        result.setBaggageInfo(resultSet.getString("baggage_info"));
        result.setCarrierAirlineNameEnglish(resultSet.getString("carrier_airline_name_en"));
        result.setCarrierIcaoCode(resultSet.getString("carrier_icao_code"));
        result.setCarrierNumber(resultSet.getString("carrier_number"));
        result.setCounter(resultSet.getString("counter"));
        result.setDepAptNameEspano(resultSet.getString("dep_apt_name_es"));
        result.setDepAptCodeIata(resultSet.getString("dep_apt_code_iata"));
        result.setEstArrDateTimeLt(resultSet.getString("est_arr_date_time_lt"));
        result.setEstDepDateTimeLt(resultSet.getString("est_dep_date_time_lt"));
        result.setFlightAirlineNameEnglish(resultSet.getString("flight_airline_name_en"));
        result.setFlightAirlineName(resultSet.getString("flight_airline_name"));
        result.setFlightIcaoCode(resultSet.getString("flight_icao_code"));
        result.setFlightNumber(resultSet.getString("flight_number"));
        result.setFltLegSeqNo(resultSet.getString("flt_leg_seq_no"));
        result.setGateInfo(resultSet.getString("gate_info"));
        result.setLoungeInfo(resultSet.getString("lounge_info"));
        result.setSchdArrOnlyDateLt(resultSet.getString("schd_arr_only_date_lt"));
        result.setSchdArrOnlyTimeLt(resultSet.getString("schd_arr_only_time_lt"));
        result.setSourceData(resultSet.getString("source_data"));
        result.setStatusInfo(resultSet.getString("status_info"));
        result.setTerminalInfo(resultSet.getString("terminal_info"));
        result.setArrTerminalInfo(resultSet.getString("arr_terminal_info"));
        result.setActDepDateTimeLt(resultSet.getString("act_dep_date_time_lt"));
        result.setSchdDepOnlyDateLt(resultSet.getString("schd_dep_only_date_lt"));
        result.setSchdDepOnlyTimeLt(resultSet.getString("schd_dep_only_time_lt"));
        result.setCreatedAt(resultSet.getLong("created_at"));

        return result;
    }
}
