package org.zerock.scheduleapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.entity.Schedule;
import org.zerock.scheduleapp.exception.NotExistException;
import org.zerock.scheduleapp.repository.ScheduleRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;

    @Transactional
    public ScheduleResponseDTO addSchedule(ScheduleRequestDTO schedule){
        Schedule s = new Schedule(schedule.getTitle(), schedule.getContent(), schedule.getAuthor(), schedule.getPassword());
        Schedule save = scheduleRepo.save(s);
        return new ScheduleResponseDTO(save.getId(), save.getTitle(), save.getContent(), save.getAuthor(), save.getCreatedAt(), save.getUpdatedAt());
    }

    public List<ScheduleResponseDTO> findAllSchedulesByAuthor(String author){
        List<Schedule> byAuthor = scheduleRepo.findByAuthor(author);
        return byAuthor.stream().map(a -> new ScheduleResponseDTO(
                a.getId(), a.getTitle(), a.getContent(), a.getAuthor(), a.getCreatedAt(), a.getUpdatedAt()
        )).toList();
    }

    public ScheduleResponseDTO viewScheduleById(Long id) {
        Schedule found = scheduleRepo.findById(id).orElseThrow(() -> new NotExistException("Schedule Not Found"));
        return new ScheduleResponseDTO(found.getId(), found.getTitle(), found.getContent(), found.getAuthor(), found.getCreatedAt(), found.getUpdatedAt());
    }

}
