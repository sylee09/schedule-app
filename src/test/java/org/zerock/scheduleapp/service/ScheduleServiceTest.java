package org.zerock.scheduleapp.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.dto.ScheduleUpdateDTO;
import org.zerock.scheduleapp.exception.NotExistException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    EntityManager em;

    private ScheduleResponseDTO scheduleResponseDTO1;
    private ScheduleResponseDTO scheduleResponseDTO2;
    private ScheduleResponseDTO scheduleResponseDTO3;

    @BeforeEach
    public void init() {
        ScheduleRequestDTO scheduleRequestDTO1 = new ScheduleRequestDTO();
        scheduleRequestDTO1.setAuthor("lee");
        scheduleRequestDTO1.setContent("테스트1");
        scheduleResponseDTO1 = scheduleService.addSchedule(scheduleRequestDTO1);

        ScheduleRequestDTO scheduleRequestDTO2 = new ScheduleRequestDTO();
        scheduleRequestDTO2.setAuthor("lee");
        scheduleRequestDTO2.setContent("테스트2");
        scheduleResponseDTO2 = scheduleService.addSchedule(scheduleRequestDTO2);

        ScheduleRequestDTO scheduleRequestDTO3 = new ScheduleRequestDTO();
        scheduleRequestDTO3.setAuthor("lee");
        scheduleRequestDTO3.setContent("테스트3");
        scheduleResponseDTO3 = scheduleService.addSchedule(scheduleRequestDTO3);
    }

    @DisplayName("스케줄 추가 함수 테스트")
    @Test
    public void saveSchedule() {
        assertThat(scheduleResponseDTO1.getAuthor()).isEqualTo("lee");
        assertThat(scheduleResponseDTO1.getCreatedAt()).isNotNull();
        assertThat(scheduleResponseDTO1.getLastUpdatedAt()).isNotNull();
    }

    @DisplayName("작성자명 기준 전체 일정 조회")
    @Test
    public void findScheduleByAuthor() {
        List<ScheduleResponseDTO> lee = scheduleService.findAllSchedulesByAuthor("lee");

        assertThat(lee.size()).isEqualTo(3);

        assertThat(lee.get(0).getContent()).isEqualTo("테스트3");
    }

    @DisplayName("스케줄 id 기준 조회")
    @Test
    public void findScheduleById() {
        ScheduleResponseDTO found = scheduleService.viewScheduleById(scheduleResponseDTO1.getId());

        assertThat(found).isEqualTo(scheduleResponseDTO1);

        assertThatThrownBy(() -> scheduleService.viewScheduleById(1000L)).isExactlyInstanceOf(NotExistException.class);
    }

    @DisplayName("스케줄 수정 메서드 테스트")
    @Test
    public void updateSchedule() throws InterruptedException {
        LocalDateTime lastUpdated = scheduleResponseDTO1.getLastUpdatedAt();

        Thread.sleep(10);

        ScheduleUpdateDTO scheduleUpdateDTO = new ScheduleUpdateDTO();
        scheduleUpdateDTO.setAuthor("park");
        scheduleUpdateDTO.setTitle("update");

        ScheduleResponseDTO updatedSchedule = scheduleService.updateSchedule(scheduleResponseDTO1.getId(), scheduleUpdateDTO);

        assertThat(updatedSchedule.getAuthor()).isEqualTo("park");
        assertThat(updatedSchedule.getTitle()).isEqualTo("update");
        assertThat(updatedSchedule.getLastUpdatedAt()).isAfter(lastUpdated);
    }

    @DisplayName("스케줄 삭제 메소드 테스트")
    @Test
    public void deleteSchedule() {
        scheduleService.deleteSchedule(scheduleResponseDTO1.getId());
        assertThatThrownBy(()->scheduleService.viewScheduleById(scheduleResponseDTO1.getId())).isExactlyInstanceOf(NotExistException.class);
    }
}