package org.zerock.scheduleapp.controller;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zerock.scheduleapp.dto.*;
import org.zerock.scheduleapp.exception.NotExistException;

import javax.security.auth.login.LoginException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ScheduleControllerTest {

    @Autowired
    private ScheduleController scheduleController;
    @Autowired
    private ReplyController replyController;

    private ScheduleResponseDTO scheduleResponseDTO;

    @BeforeEach
    void setUp() {
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setTitle("테스트1");
        scheduleRequestDTO.setContent("테스트");
        scheduleRequestDTO.setAuthor("테스터");
        scheduleRequestDTO.setPassword("123456");
        ResponseEntity<ScheduleResponseDTO> scheduleResponseDTOResponseEntity = scheduleController.addSchedule(scheduleRequestDTO);
        scheduleResponseDTO = scheduleResponseDTOResponseEntity.getBody();

        ReplyRequestDTO replyRequestDTO = new ReplyRequestDTO();
        replyRequestDTO.setContent("test");
        replyRequestDTO.setAuthor("test");
        replyRequestDTO.setPassword("test");

        replyController.addReply(scheduleResponseDTO.getId(),replyRequestDTO);
    }

    @Test
    void addSchedule() {
        assertThat(scheduleResponseDTO.getId()).isNotNull();
        assertThat(scheduleResponseDTO.getTitle()).isEqualTo("테스트1");
        assertThat(scheduleResponseDTO.getContent()).isEqualTo("테스트");
        assertThat(scheduleResponseDTO.getAuthor()).isEqualTo("테스터");
        assertThat(scheduleResponseDTO.getCreatedAt()).isEqualTo(scheduleResponseDTO.getLastUpdatedAt());
    }

    @Test
    void getSchedule() {
        ResponseEntity<ScheduleResponseDTO> scheduleResponseDTOResponseEntity = scheduleController.getSchedule(scheduleResponseDTO.getId());
        ScheduleResponseDTO scheduleResponseDTO = scheduleResponseDTOResponseEntity.getBody();

        assertThat(scheduleResponseDTO.getAuthor()).isEqualTo(scheduleResponseDTO.getAuthor());
        assertThat(scheduleResponseDTO.getReplies().size()).isEqualTo(1);
        assertThat(scheduleResponseDTO.getReplies().get(0).getAuthor()).isEqualTo("test");
        assertThat(scheduleResponseDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateSchedule() {
        ScheduleUpdateDTO scheduleUpdateDTO = new ScheduleUpdateDTO();
        scheduleUpdateDTO.setTitle("modified");
        scheduleUpdateDTO.setAuthor("modified");
        scheduleUpdateDTO.setPassword("123456");
        ResponseEntity<ScheduleResponseDTO> scheduleResponseDTOResponseEntity = scheduleController.updateSchedule(scheduleResponseDTO.getId(), scheduleUpdateDTO);
        ScheduleResponseDTO updatedScheduleResponseDTO = scheduleResponseDTOResponseEntity.getBody();

        assertThat(updatedScheduleResponseDTO.getTitle()).isEqualTo("modified");
        assertThat(updatedScheduleResponseDTO.getAuthor()).isEqualTo("modified");

        scheduleUpdateDTO.setPassword("1111");
        ResponseEntity<ScheduleResponseDTO> anotherScheduleResponseDTOResponseEntity = scheduleController.updateSchedule(scheduleResponseDTO.getId(), scheduleUpdateDTO);
        assertThat(anotherScheduleResponseDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void deleteSchedule() {
        ScheduleDeleteDTO scheduleDeleteDTO = new ScheduleDeleteDTO();
        scheduleDeleteDTO.setPassword("1111");
        ResponseEntity<Void> voidResponseEntity1 = scheduleController.deleteSchedule(scheduleResponseDTO.getId(), scheduleDeleteDTO);
        assertThat(voidResponseEntity1.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        scheduleDeleteDTO.setPassword("123456");
        ResponseEntity<Void> voidResponseEntity = scheduleController.deleteSchedule(scheduleResponseDTO.getId(), scheduleDeleteDTO);
        assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}