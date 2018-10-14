package com.passengers.friend.executor.service;

import com.passengers.friend.data.entity.DebriefingTask;
import com.passengers.friend.data.entity.DestinationData;
import com.passengers.friend.data.entity.EWorkType;
import com.passengers.friend.data.repository.DebriefingTaskDao;
import com.passengers.friend.data.repository.DestinationDataDao;
import com.passengers.friend.data.service.DebriefingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExecutorServiceImpl implements ExecutorService {

    private final DebriefingService debriefingService;

    private final DestinationDataDao destinationDataDao;

    private final DebriefingTaskDao debriefingTaskDao;

    @Transactional
    @Override
    public void transform(DebriefingTask task){
        DestinationData data = destinationDataDao.getByDestination(task.getDay(), task.getIcao(), task.getNumber());
        if(data == null){
            data = debriefingService.transform(task);
            destinationDataDao.save(data);
        }
        task.setType(EWorkType.DONE);
        debriefingTaskDao.save(task);
    }
}
