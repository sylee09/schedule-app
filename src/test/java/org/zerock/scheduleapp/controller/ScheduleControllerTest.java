package org.zerock.scheduleapp.controller;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.scheduleapp.dto.ScheduleDeleteDTO;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.dto.ScheduleUpdateDTO;
import org.zerock.scheduleapp.exception.LoginException;
import org.zerock.scheduleapp.exception.NotExistException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleControllerTest {

    @Autowired
    private ScheduleController controller;
    private ScheduleResponseDTO scheduleResponseDTO;

    @BeforeEach
    void setUp() {
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setTitle("테스트1");
        scheduleRequestDTO.setContent("테스트");
        scheduleRequestDTO.setAuthor("테스터");
        scheduleRequestDTO.setPassword("123456");
        scheduleResponseDTO = controller.addSchedule(scheduleRequestDTO);
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
        ScheduleResponseDTO schedule = controller.getSchedule(scheduleResponseDTO.getId());
        assertThat(schedule).isEqualTo(scheduleResponseDTO);
    }

    @Test
    void updateSchedule() {
        ScheduleUpdateDTO scheduleUpdateDTO = new ScheduleUpdateDTO();
        scheduleUpdateDTO.setTitle("modified");
        scheduleUpdateDTO.setAuthor("modified");
        scheduleUpdateDTO.setPassword("123456");
        ScheduleResponseDTO updatedSchedule = controller.updateSchedule(scheduleResponseDTO.getId(), scheduleUpdateDTO);
        assertThat(updatedSchedule.getTitle()).isEqualTo("modified");
        assertThat(updatedSchedule.getAuthor()).isEqualTo("modified");

        scheduleUpdateDTO.setPassword("1111");
        Assertions.assertThatThrownBy(() -> controller.updateSchedule(scheduleResponseDTO.getId(), scheduleUpdateDTO)).isExactlyInstanceOf(LoginException.class);
    }

    @Test
    void deleteSchedule() {
        ScheduleDeleteDTO scheduleDeleteDTO = new ScheduleDeleteDTO();
        scheduleDeleteDTO.setPassword("1111");
        assertThatThrownBy(() -> controller.deleteSchedule(scheduleResponseDTO.getId(), scheduleDeleteDTO)).isExactlyInstanceOf(LoginException.class);

        scheduleDeleteDTO.setPassword("123456");
        controller.deleteSchedule(scheduleResponseDTO.getId(), scheduleDeleteDTO);

        assertThatThrownBy(() -> controller.getSchedule(scheduleResponseDTO.getId())).isExactlyInstanceOf(NotExistException.class);
    }
}