package com.passengers.friend.data.repository;

import com.passengers.friend.data.entity.DestinationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DestinationDataDao extends JpaRepository<DestinationData, Long> {

    @Query(nativeQuery = true,
            value ="select * from aenaflight_source d where date(d.schd_dep_lt) = :day and d.flight_code = :code and d.flight_number = :num LIMIT 1")
    DestinationData getByDestination(@Param("day")LocalDate day, @Param("code")String code, @Param("num")String num);
}
