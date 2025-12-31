package org.zerock.scheduleapp.controller;

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
import org.zerock.scheduleapp.exception.RepliesLimitException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyControllerTest {

    @Autowired
    ReplyController replyController;
    @Autowired
    ScheduleController scheduleController;
    ScheduleResponseDTO scheduleResponseDTO;


    @BeforeEach
    void init() {
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setTitle("test");
        scheduleRequestDTO.setContent("test");
        scheduleRequestDTO.setPassword("test");
        scheduleResponseDTO = scheduleController.addSchedule(scheduleRequestDTO);
    }

    @Test
    void addReply() {
        ReplyRequestDTO replyRequestDTO = new ReplyRequestDTO();
        replyRequestDTO.setContent("test");
        replyRequestDTO.setAuthor("test");
        replyRequestDTO.setPassword("test");

        ReplyResponseDTO replyResponseDTO = replyController.addReply(scheduleResponseDTO.getId(), replyRequestDTO);
        assertThat(replyResponseDTO).isNotNull();
        assertThat(replyResponseDTO.getAuthor()).isEqualTo("test");
    }

    @Test
    void add11Replies() {
        ReplyResponseDTO replyResponseDTO;
        for (int i = 0; i <= 9; i++) {
            ReplyRequestDTO replyRequestDTO = new ReplyRequestDTO();
            replyRequestDTO.setContent("test");
            replyRequestDTO.setAuthor("test");
            replyRequestDTO.setPassword("test");
            replyController.addReply(scheduleResponseDTO.getId(), replyRequestDTO);
        }

        ReplyRequestDTO replyRequestDTO = new ReplyRequestDTO();
        replyRequestDTO.setContent("test");
        replyRequestDTO.setAuthor("test");
        replyRequestDTO.setPassword("test");
        assertThatThrownBy(() -> replyController.addReply(scheduleResponseDTO.getId(), replyRequestDTO)).isExactlyInstanceOf(RepliesLimitException.class);
    }
}