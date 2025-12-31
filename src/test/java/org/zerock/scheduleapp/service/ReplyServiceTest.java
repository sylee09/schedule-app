package org.zerock.scheduleapp.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.scheduleapp.dto.ReplyRequestDTO;
import org.zerock.scheduleapp.dto.ReplyResponseDTO;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.entity.Schedule;

@SpringBootTest
//@Transactional
class ReplyServiceTest {

    ScheduleResponseDTO scheduleResponseDTO;
    @Autowired
    ReplyService replyService;
    @Autowired
    ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setTitle("test");
        scheduleRequestDTO.setContent("test");
        scheduleRequestDTO.setAuthor("tester");
        scheduleRequestDTO.setPassword("1234");
        scheduleResponseDTO = scheduleService.addSchedule(scheduleRequestDTO);
    }

    @Test
    void addReply() {
        ReplyRequestDTO replyRequestDTO = new ReplyRequestDTO();
        replyRequestDTO.setAuthor("tester");
        replyRequestDTO.setContent("test");
        replyRequestDTO.setPassword("test");

        ReplyResponseDTO replyResponseDTO = replyService.addReply(replyRequestDTO, scheduleResponseDTO.getId());
        Assertions.assertThat(replyResponseDTO.getAuthor()).isEqualTo("tester");
    }
}