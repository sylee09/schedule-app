package org.zerock.scheduleapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.scheduleapp.entity.Reply;
import org.zerock.scheduleapp.dto.ReplyRequestDTO;
import org.zerock.scheduleapp.dto.ReplyResponseDTO;
import org.zerock.scheduleapp.entity.Schedule;
import org.zerock.scheduleapp.exception.NotExistException;
import org.zerock.scheduleapp.repository.ReplyRepo;
import org.zerock.scheduleapp.repository.ScheduleRepo;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepo replyRepo;
    private final ScheduleRepo scheduleRepo;

    @Transactional
    public ReplyResponseDTO addReply(ReplyRequestDTO replyRequestDTO, Long scheduleId) {
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(() -> new NotExistException("존재하지 않는 스케줄입니다."));
        Reply reply = new Reply(replyRequestDTO.getContent(), replyRequestDTO.getAuthor(), replyRequestDTO.getPassword());
        reply.setSchedule(schedule);
        Reply save = replyRepo.save(reply);
        return new ReplyResponseDTO(save.getId(), save.getContent(), save.getAuthor(), save.getCreatedAt(), save.getUpdatedAt());
    }

    public boolean isPossibleToAddReply(Long scheduleId) {
        Long cnt = replyRepo.countByScheduleId(scheduleId);
        return cnt < 10;
    }
}
