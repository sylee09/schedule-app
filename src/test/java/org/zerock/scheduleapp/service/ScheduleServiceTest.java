package org.zerock.scheduleapp.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;

import java.util.List;

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

    @DisplayName("작성자명 기준 전체 일정 조회")
    @Test
    public void findScheduleByAuthor() {
        ScheduleRequestDTO scheduleRequestDTO1 = new ScheduleRequestDTO();
        scheduleRequestDTO1.setAuthor("lee");
        scheduleRequestDTO1.setContent("테스트1");
        scheduleService.addSchedule(scheduleRequestDTO1);

        ScheduleRequestDTO scheduleRequestDTO2 = new ScheduleRequestDTO();
        scheduleRequestDTO2.setAuthor("lee");
        scheduleRequestDTO2.setContent("테스트2");
        scheduleService.addSchedule(scheduleRequestDTO2);

        ScheduleRequestDTO scheduleRequestDTO3 = new ScheduleRequestDTO();
        scheduleRequestDTO3.setAuthor("lee");
        scheduleRequestDTO3.setContent("테스트3");
        scheduleService.addSchedule(scheduleRequestDTO3);

        List<ScheduleResponseDTO> lee = scheduleService.findAllSchedulesByAuthor("lee");

        assertThat(lee.size()).isEqualTo(3);

        assertThat(lee.get(0).getContent()).isEqualTo("테스트3");
    }
}