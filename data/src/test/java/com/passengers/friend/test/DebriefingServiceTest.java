package com.passengers.friend.test;

import com.passengers.friend.data.Application;
import com.passengers.friend.data.entity.DebriefingTask;
import com.passengers.friend.data.entity.DestinationData;
import com.passengers.friend.data.entity.SourcesData;
import com.passengers.friend.data.service.DebriefingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class DebriefingServiceTest {

    @Autowired
    private DebriefingService service;

    @Test
    public void testSources() throws Exception {
        List<SourcesData> lists = service.getData("IBE", "5225", "03/01/17");
        //lists.forEach(item -> log.info(item.toString()));

        Assert.assertTrue(!lists.isEmpty());
    }

    @Test
    public void dateFormat() throws Exception {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        String strDate = "04/01/17 07:41";
        LocalDateTime dateTime = LocalDateTime.parse(strDate, format);

        Assert.assertEquals(dateTime, LocalDateTime.of(2017, 1 , 4, 7, 41));
    }
    @Test
    public void testTransform() throws Exception {
        DebriefingTask task = new DebriefingTask();
        task.setDay(LocalDate.of(2017, 1, 3));
        task.setIcao("IBE");
        task.setNumber("5225");
        DestinationData data = service.transform(task);

        log.info(data.toString());

        DestinationData dataTest = new DestinationData();
        dataTest.setAdep("ACE");
        dataTest.setAdes("AGP");
        dataTest.setFlightCode("IBE");
        dataTest.setFlightNumber("5225");
        dataTest.setCarrierCode("VLG");
        dataTest.setCarrierCode("3141");
        dataTest.setStatusInfo("The flight landed");
        dataTest.setSchdDepLt(LocalDateTime.of(2017, 1 , 3, 11, 30));
        dataTest.setSchdArrLt(LocalDateTime.of(2017, 1 , 3, 14, 20));
        dataTest.setEstDepLt(LocalDateTime.of(2017, 1 , 3, 11, 36));
        dataTest.setEstArrLt(LocalDateTime.of(2017, 1 , 3, 14, 42));
        dataTest.setActDepLt(LocalDateTime.of(2017, 1 , 3, 11, 45));
        dataTest.setActArrLt(LocalDateTime.of(2017, 1 , 3, 14, 39));
        dataTest.setSeqNo(1);
        dataTest.setAircraftNameScheduled("AIRBUS A320");
        dataTest.setBaggageInfo("21");
        dataTest.setCounter("39 - 40,23 - 24");
        dataTest.setGateInfo("9");
        dataTest.setLoungeInfo("2");
        dataTest.setTerminalInfo("1");
        dataTest.setArrTerminalInfo("2");
        dataTest.setSourceData("http://www.aena.es/csee/Satellite/infovuelos/en/?nvuelo=5225&company=IBE&hprevista=2017-01-03+14:20");

        Assert.assertEquals(data, dataTest);
    }
    @Test
    public void testTask() throws Exception {
        Page<DebriefingTask> page = service.getNewTask();
        Assert.assertNotNull(page);
    }


}
