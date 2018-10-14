package com.passengers.friend.data.repository;

import com.passengers.friend.data.entity.DebriefingTask;
import com.passengers.friend.data.entity.EWorkType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface DebriefingTaskDao extends JpaRepository<DebriefingTask, Long> {

    DebriefingTask getById(Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select t from DebriefingTask t where t.type = :type")
    Page<DebriefingTask> getAndLockIsNew(@Param("type")EWorkType type, Pageable page);
}
