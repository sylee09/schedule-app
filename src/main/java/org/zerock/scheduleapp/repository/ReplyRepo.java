package org.zerock.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.scheduleapp.entity.Reply;

import java.util.List;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    @Query("select count(*) from Reply r where r.schedule.id = :id")
    Long countByScheduleId(@Param("id") Long scheduleId);

    @Query("select r from Reply r where r.schedule.id=:id")
    List<Reply> findAllByScheduleId(@Param("id") Long scheduleId);
}
