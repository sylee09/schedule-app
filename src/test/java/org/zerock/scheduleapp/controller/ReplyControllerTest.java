package org.zerock.scheduleapp.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zerock.scheduleapp.dto.ReplyRequestDTO;
import org.zerock.scheduleapp.dto.ReplyResponseDTO;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        ResponseEntity<ScheduleResponseDTO> scheduleResponseDTOResponseEntity = scheduleController.addSchedule(scheduleRequestDTO);
        scheduleResponseDTO = scheduleResponseDTOResponseEntity.getBody();
    }

    @Test
    void addReply() {
        ReplyRequestDTO replyRequestDTO = new ReplyRequestDTO();
        replyRequestDTO.setContent("test");
        replyRequestDTO.setAuthor("test");
        replyRequestDTO.setPassword("test");

        ResponseEntity<ReplyResponseDTO> replyResponseDTOResponseEntity = replyController.addReply(scheduleResponseDTO.getId(), replyRequestDTO);
        assertThat(replyResponseDTOResponseEntity).isNotNull();
        assertThat(replyResponseDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(replyResponseDTOResponseEntity.getBody().getAuthor()).isEqualTo("test");
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
        ResponseEntity<ReplyResponseDTO> replyResponseDTOResponseEntity = replyController.addReply(scheduleResponseDTO.getId(), replyRequestDTO);
        assertThat(replyResponseDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}