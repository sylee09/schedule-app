package org.zerock.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.scheduleapp.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s join fetch s.replies where s.author like :name order by s.updatedAt desc")
    List<Schedule> findByAuthor(@Param("name") String name);

    @Query("select s from Schedule s join fetch s.replies order by s.updatedAt desc")
    List<Schedule> findAll();

}
