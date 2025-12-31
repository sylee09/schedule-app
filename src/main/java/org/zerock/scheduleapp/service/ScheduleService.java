package org.zerock.scheduleapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.entity.Schedule;
import org.zerock.scheduleapp.repository.ScheduleRepo;

@Repository
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;

    public ScheduleResponseDTO addSchedule(ScheduleRequestDTO schedule){
        Schedule s = new Schedule(schedule.getTitle(), schedule.getContent(), schedule.getAuthor(), schedule.getPassword());
        Schedule save = scheduleRepo.save(s);
        return new ScheduleResponseDTO(save.getId(), save.getTitle(), save.getContent(), save.getAuthor(), save.getCreatedAt(), save.getUpdatedAt());
    }
}
