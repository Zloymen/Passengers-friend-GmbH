package com.passengers.friend.data.service;

import com.passengers.friend.data.entity.DebriefingTask;
import com.passengers.friend.data.entity.DestinationData;
import com.passengers.friend.data.entity.EWorkType;
import com.passengers.friend.data.entity.SourcesData;
import com.passengers.friend.data.mapper.SourcesRowMapper;
import com.passengers.friend.data.reader.SqlReader;
import com.passengers.friend.data.repository.DebriefingTaskDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebriefingServiceImpl implements DebriefingService {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_SOURCES = "select * from aenaflight_2017_01 where flight_icao_code = ? and\n" +
            "flight_number= ? and schd_dep_only_date_lt= ? order by id desc";

    private String updateTask;

    private final DebriefingTaskDao debriefingTaskDao;

    @Value("classpath:update-task.sql")
    private Resource resourceFile;


    private final ResourceLoader resourceLoader;

    @PostConstruct
    private void postConstruct() throws IOException {
        updateTask = SqlReader.getStatement(resourceLoader.getResource("classpath:update-task.sql").getInputStream());
    }


    private static final Predicate<String> PREDICATE_IMPORTANT_VALUES  = item ->  !StringUtils.isEmpty(item) && !item.equals("-");

    @Override
    @Transactional
    public void updateTask() {
        jdbcTemplate.execute(updateTask);
    }

    @Override
    public DestinationData transform(DebriefingTask task) {
        List<SourcesData> sourcesData = getData(task.getIcao(), task.getNumber(), task.getDay().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        DestinationData data = new DestinationData();
        for(SourcesData source : sourcesData){

            if(Objects.isNull(data.getAdep()) && !StringUtils.isEmpty(source.getDepAptCodeIata())){
                data.setAdep(source.getDepAptCodeIata());
            }

            if(Objects.isNull(data.getAdes()) && !StringUtils.isEmpty(source.getArrAptCodeIata())){
                data.setAdes(source.getArrAptCodeIata());
            }

            if(Objects.isNull(data.getFlightCode()) && !StringUtils.isEmpty(source.getFlightIcaoCode())){
                data.setFlightCode(source.getFlightIcaoCode());
            }

            if(Objects.isNull(data.getFlightNumber()) && !StringUtils.isEmpty(source.getFlightNumber())){
                data.setFlightNumber(source.getFlightNumber());
            }

            if(Objects.isNull(data.getCarrierCode()) && !StringUtils.isEmpty(source.getCarrierIcaoCode())){
                data.setCarrierCode(source.getCarrierIcaoCode());
            }

            if(Objects.isNull(data.getCarrierNumber()) && !StringUtils.isEmpty(source.getCarrierNumber())){
                data.setCarrierCode(source.getCarrierNumber());
            }

            if(Objects.isNull(data.getStatusInfo()) && !StringUtils.isEmpty(source.getStatusInfo())){
                data.setStatusInfo(source.getStatusInfo());
            }

            if(Objects.isNull(data.getSchdDepLt()) && !StringUtils.isEmpty(source.getSchdDepOnlyDateLt()) && !StringUtils.isEmpty(source.getSchdDepOnlyTimeLt())){
                data.setSchdDepLt(LocalDateTime.parse(source.getSchdDepOnlyDateLt() + " " + source.getSchdDepOnlyTimeLt(), format));
            }

            if(Objects.isNull(data.getSchdArrLt()) && !StringUtils.isEmpty(source.getSchdArrOnlyDateLt()) && !StringUtils.isEmpty(source.getSchdArrOnlyTimeLt())){
                data.setSchdArrLt(LocalDateTime.parse(source.getSchdArrOnlyDateLt() + " " + source.getSchdArrOnlyTimeLt(), format));
            }
            //estDepLt
            if(Objects.isNull(data.getEstDepLt()) && !StringUtils.isEmpty(source.getEstDepDateTimeLt())){
                data.setEstDepLt(LocalDateTime.parse(source.getEstDepDateTimeLt(), format));
            }
            //estArrLt
            if(Objects.isNull(data.getEstArrLt()) && !StringUtils.isEmpty(source.getEstArrDateTimeLt())){
                data.setEstArrLt(LocalDateTime.parse(source.getEstArrDateTimeLt(), format));
            }

            if(Objects.isNull(data.getActArrLt()) && !StringUtils.isEmpty(source.getActArrDateTimeLt())){
                data.setActArrLt(LocalDateTime.parse(source.getActArrDateTimeLt(), format));
            }

            if(Objects.isNull(data.getActDepLt()) && !StringUtils.isEmpty(source.getActDepDateTimeLt())){
                data.setActDepLt(LocalDateTime.parse(source.getActDepDateTimeLt(), format));
            }
            //seqNo
            if(Objects.isNull(data.getSeqNo()) && !StringUtils.isEmpty(source.getFltLegSeqNo())){
                data.setSeqNo(Integer.valueOf(source.getFltLegSeqNo()));
            }
            //aircraftNameScheduled
            if(Objects.isNull(data.getAircraftNameScheduled()) && !StringUtils.isEmpty(source.getAircraftNameScheduled())){
                data.setAircraftNameScheduled(source.getAircraftNameScheduled());
            }
            //sourceData
            if(Objects.isNull(data.getSourceData()) && !StringUtils.isEmpty(source.getSourceData())){
                data.setSourceData(source.getSourceData());
            }

            if(data.isFullData()) break;
        }

        data.setBaggageInfo(sourcesData.stream().map(SourcesData::getBaggageInfo)
                .distinct()
                .filter(PREDICATE_IMPORTANT_VALUES)
                .collect(Collectors.joining(",")));
        data.setCounter(sourcesData.stream().map(SourcesData::getCounter)
                .filter(PREDICATE_IMPORTANT_VALUES)
                .distinct()
                .collect(Collectors.joining(",")));
        data.setGateInfo(sourcesData.stream().map(SourcesData::getGateInfo)
                .distinct()
                .filter(PREDICATE_IMPORTANT_VALUES)
                .collect(Collectors.joining(",")));
        data.setLoungeInfo(sourcesData.stream().map(SourcesData::getLoungeInfo)
                .distinct()
                .filter(PREDICATE_IMPORTANT_VALUES)
                .collect(Collectors.joining(",")));
        data.setTerminalInfo(sourcesData.stream().map(SourcesData::getTerminalInfo)
                .distinct()
                .filter(PREDICATE_IMPORTANT_VALUES)
                .collect(Collectors.joining(",")));
        data.setArrTerminalInfo(sourcesData.stream().map(SourcesData::getArrTerminalInfo)
                .distinct()
                .filter(PREDICATE_IMPORTANT_VALUES)
                .collect(Collectors.joining(",")));

        return data;
    }


    @Override
    public List<SourcesData> getData(String icao, String number, String day) {
        return jdbcTemplate.query(SELECT_SOURCES, new Object[] { icao, number, day }, new SourcesRowMapper());
    }

    @Override
    @Transactional
    public Page<DebriefingTask> getNewTask(){
        return debriefingTaskDao.getAndLockIsNew(EWorkType.NEW, PageRequest.of(0, 100));
    }


}
