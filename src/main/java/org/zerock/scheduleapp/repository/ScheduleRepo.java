package org.zerock.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.scheduleapp.entity.Schedule;

import java.util.List;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where s.author like :name order by s.updatedAt desc")
    List<Schedule> findByAuthor(@Param("name") String name);
}
