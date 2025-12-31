package org.zerock.scheduleapp.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    @DisplayName("스케줄 추가 함수 테스트")
    @Test
    public void saveSchedule() {
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setAuthor("lee");
        ScheduleResponseDTO scheduleResponseDTO = scheduleService.addSchedule(scheduleRequestDTO);

        assertThat(scheduleResponseDTO.getAuthor()).isEqualTo("lee");
        assertThat(scheduleResponseDTO.getCreatedAt()).isNotNull();
        assertThat(scheduleResponseDTO.getLastUpdatedAt()).isNotNull();
    }
}