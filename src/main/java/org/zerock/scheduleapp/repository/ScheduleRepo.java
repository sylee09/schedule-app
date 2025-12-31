package org.zerock.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.scheduleapp.entity.Schedule;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

}
