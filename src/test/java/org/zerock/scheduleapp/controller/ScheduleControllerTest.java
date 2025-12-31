package org.zerock.scheduleapp.controller;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleControllerTest {

    @Autowired
    private ScheduleController controller;

    @Test
    void addSchedule() {
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setTitle("테스트1");
        scheduleRequestDTO.setContent("테스트");
        scheduleRequestDTO.setAuthor("테스터");
        scheduleRequestDTO.setPassword("123456");

        ScheduleResponseDTO scheduleResponseDTO = controller.addSchedule(scheduleRequestDTO);

        assertThat(scheduleResponseDTO.getId()).isNotNull();
        assertThat(scheduleResponseDTO.getTitle()).isEqualTo("테스트1");
        assertThat(scheduleResponseDTO.getContent()).isEqualTo("테스트");
        assertThat(scheduleResponseDTO.getAuthor()).isEqualTo("테스터");
        assertThat(scheduleResponseDTO.getCreatedAt()).isEqualTo(scheduleResponseDTO.getLastUpdatedAt());
    }

    @Test
    void getSchedule() {
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setTitle("테스트1");
        scheduleRequestDTO.setContent("테스트");
        scheduleRequestDTO.setAuthor("테스터");
        scheduleRequestDTO.setPassword("123456");

        ScheduleResponseDTO scheduleResponseDTO = controller.addSchedule(scheduleRequestDTO);

        ScheduleResponseDTO schedule = controller.getSchedule(scheduleResponseDTO.getId());
        assertThat(schedule).isEqualTo(scheduleResponseDTO);
    }
}