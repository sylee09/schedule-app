package org.zerock.scheduleapp.controller;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.scheduleapp.dto.*;
import org.zerock.scheduleapp.exception.LoginException;
import org.zerock.scheduleapp.exception.NotExistException;

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
        scheduleResponseDTO = scheduleController.addSchedule(scheduleRequestDTO);

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
        ScheduleResponseDTO schedule = scheduleController.getSchedule(scheduleResponseDTO.getId());
        assertThat(schedule.getAuthor()).isEqualTo(scheduleResponseDTO.getAuthor());
        assertThat(schedule.getReplies().size()).isEqualTo(1);
        assertThat(schedule.getReplies().get(0).getAuthor()).isEqualTo("test");
    }

    @Test
    void updateSchedule() {
        ScheduleUpdateDTO scheduleUpdateDTO = new ScheduleUpdateDTO();
        scheduleUpdateDTO.setTitle("modified");
        scheduleUpdateDTO.setAuthor("modified");
        scheduleUpdateDTO.setPassword("123456");
        ScheduleResponseDTO updatedSchedule = scheduleController.updateSchedule(scheduleResponseDTO.getId(), scheduleUpdateDTO);
        assertThat(updatedSchedule.getTitle()).isEqualTo("modified");
        assertThat(updatedSchedule.getAuthor()).isEqualTo("modified");

        scheduleUpdateDTO.setPassword("1111");
        Assertions.assertThatThrownBy(() -> scheduleController.updateSchedule(scheduleResponseDTO.getId(), scheduleUpdateDTO)).isExactlyInstanceOf(LoginException.class);
    }

    @Test
    void deleteSchedule() {
        ScheduleDeleteDTO scheduleDeleteDTO = new ScheduleDeleteDTO();
        scheduleDeleteDTO.setPassword("1111");
        assertThatThrownBy(() -> scheduleController.deleteSchedule(scheduleResponseDTO.getId(), scheduleDeleteDTO)).isExactlyInstanceOf(LoginException.class);

        scheduleDeleteDTO.setPassword("123456");
        scheduleController.deleteSchedule(scheduleResponseDTO.getId(), scheduleDeleteDTO);

        assertThatThrownBy(() -> scheduleController.getSchedule(scheduleResponseDTO.getId())).isExactlyInstanceOf(NotExistException.class);
    }
}