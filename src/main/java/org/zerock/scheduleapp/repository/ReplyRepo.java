package org.zerock.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.scheduleapp.entity.Reply;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    @Query("select count(*) from Reply r where r.schedule.id = :id")
    Long countByScheduleId(@Param("id") Long scheduleId);
}
