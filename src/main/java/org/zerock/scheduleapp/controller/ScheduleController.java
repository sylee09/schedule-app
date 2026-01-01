package org.zerock.scheduleapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zerock.scheduleapp.dto.ScheduleDeleteDTO;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.dto.ScheduleUpdateDTO;
import org.zerock.scheduleapp.entity.Schedule;
import org.zerock.scheduleapp.exception.LoginException;
import org.zerock.scheduleapp.service.ScheduleService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDTO> addSchedule(@RequestBody @Validated ScheduleRequestDTO schedule) {
        ScheduleResponseDTO scheduleResponseDTO = service.addSchedule(schedule);
        return ResponseEntity.created(URI.create("/schedules")).body(scheduleResponseDTO);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDTO> getSchedule(@PathVariable Long id) {
        ScheduleResponseDTO scheduleResponseDTO = service.viewScheduleById(id);
        return ResponseEntity.ok(scheduleResponseDTO);
    }

    @PatchMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @RequestBody @Validated ScheduleUpdateDTO updateDTO) {
        if (service.checkValidPassword(id, updateDTO.getPassword())) {
            ScheduleResponseDTO scheduleResponseDTO = service.updateSchedule(id, updateDTO);
            return ResponseEntity.ok(scheduleResponseDTO);
        } else {
            throw new LoginException("Wrong Password");
        }
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody @Validated ScheduleDeleteDTO deleteDTO) {
        if (service.checkValidPassword(id, deleteDTO.getPassword())) {
            service.deleteSchedule(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new LoginException("Wrong Password");
        }
    }
}
