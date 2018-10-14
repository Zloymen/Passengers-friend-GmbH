insert into debriefing_task (flight_icao_code, flight_number, day)
  select
    flight_icao_code,
    flight_number,
    to_date(s.schd_dep_only_date_lt, 'dd/mm/yy')
  from aenaflight_2017_01 s
  where
    schd_dep_only_date_lt != '' and schd_arr_only_date_lt != ''
    and not exists(select 1
                   from debriefing_task dt
                   where dt.day = to_date(s.schd_dep_only_date_lt, 'dd/mm/yy') and
                         dt.flight_number = s.flight_number and dt.flight_icao_code = s.flight_icao_code)
  group by flight_icao_code, flight_number, schd_dep_only_date_lt;