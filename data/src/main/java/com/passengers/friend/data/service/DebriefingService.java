package com.passengers.friend.data.service;

import com.passengers.friend.data.entity.DebriefingTask;
import com.passengers.friend.data.entity.DestinationData;
import com.passengers.friend.data.entity.SourcesData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DebriefingService {

    void updateTask();
    DestinationData transform(DebriefingTask task);
    List<SourcesData> getData(String icao, String number, String day);
    Page<DebriefingTask> getNewTask();
}
