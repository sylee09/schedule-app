package org.zerock.scheduleapp.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.scheduleapp.dto.ReplyResponseDTO;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.dto.ScheduleUpdateDTO;
import org.zerock.scheduleapp.entity.Reply;
import org.zerock.scheduleapp.entity.Schedule;
import org.zerock.scheduleapp.exception.NotExistException;
import org.zerock.scheduleapp.repository.ReplyRepo;
import org.zerock.scheduleapp.repository.ScheduleRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final ReplyRepo replyRepo;

    @Transactional
    public ScheduleResponseDTO addSchedule(ScheduleRequestDTO schedule){
        Schedule s = new Schedule(schedule.getTitle(), schedule.getContent(), schedule.getAuthor(), schedule.getPassword());
        Schedule save = scheduleRepo.save(s);
        return new ScheduleResponseDTO(save.getId(), save.getTitle(), save.getContent(), save.getAuthor(), null, save.getCreatedAt(), save.getUpdatedAt());
    }

    public List<ScheduleResponseDTO> findAllSchedulesByAuthor(String author){
        return scheduleRepo.findByAuthor(author)
                .stream()
                // reply를 ReplyResponseDTO로 변환
                .map(s -> {
                    List<ReplyResponseDTO> replies = s.getReplies()
                            .stream()
                            .map(r -> new ReplyResponseDTO(r.getId(), r.getContent(), r.getAuthor(), r.getCreatedAt(), r.getUpdatedAt()))
                            .toList();
                    // 변환된 reply를 ScheduleResponseDTO에 넣는다.
                    return new ScheduleResponseDTO(s.getId(), s.getTitle(), s.getContent(), s.getAuthor(), replies, s.getCreatedAt(), s.getUpdatedAt());
                }).toList();
    }

    public List<ScheduleResponseDTO> findAllSchedules() {
        return scheduleRepo.findAll()
                // reply를 ReplyResponseDTO로 변환
                .stream().map(s -> {
                    List<ReplyResponseDTO> replies = s.getReplies().stream()
                            .map(r -> new ReplyResponseDTO(r.getId(), r.getContent(), r.getAuthor(), r.getCreatedAt(), r.getUpdatedAt()))
                            .toList();

                    // 변환된 reply를 ScheduleResponseDTO에 넣는다.
                    return new ScheduleResponseDTO(
                            s.getId(),
                            s.getTitle(),
                            s.getContent(),
                            s.getAuthor(),
                            replies,
                            s.getCreatedAt(),
                            s.getUpdatedAt()
                    );
                }).toList();
    }

    public ScheduleResponseDTO viewScheduleById(Long id) {
        Schedule found = scheduleRepo.findById(id).orElseThrow(() -> new NotExistException("Schedule Not Found"));
        List<ReplyResponseDTO> list = replyRepo.findAllByScheduleId(id).stream().map(r -> new ReplyResponseDTO(r.getId(), r.getContent(), r.getAuthor(), r.getCreatedAt(), r.getUpdatedAt())).toList();
        return new ScheduleResponseDTO(found.getId(), found.getTitle(), found.getContent(), found.getAuthor(), list, found.getCreatedAt(), found.getUpdatedAt());
    }

    @Transactional
    public ScheduleResponseDTO updateSchedule(Long id, ScheduleUpdateDTO dto) {
        Schedule found = scheduleRepo.findById(id).orElseThrow(() -> new NotExistException("Schedule Not Found"));
        found.setAuthor(dto.getAuthor());
        found.setTitle(dto.getTitle());
        List<Reply> replies = replyRepo.findAllByScheduleId(id);
        List<ReplyResponseDTO> list = replies.stream().map(r -> new ReplyResponseDTO(r.getId(), r.getContent(), r.getAuthor(), r.getCreatedAt(), r.getUpdatedAt())).toList();

        // EntityManager를 직접 받아서 em.flush(), em.clear() 하는 것은 위험하므로 이 방법을 채택
        scheduleRepo.saveAndFlush(found);
        return new ScheduleResponseDTO(found.getId(), found.getTitle(), found.getContent(), found.getAuthor(), list, found.getCreatedAt(), found.getUpdatedAt());
    }

    public boolean checkValidPassword(Long id, String password){
        Schedule found = scheduleRepo.findById(id).orElseThrow(() -> new NotExistException("Schedule Not Found"));
        String pwd = found.getPassword();
        return pwd.equals(password);
    }

    @Transactional
    public void deleteSchedule(Long id){
        scheduleRepo.deleteById(id);
    }

}
