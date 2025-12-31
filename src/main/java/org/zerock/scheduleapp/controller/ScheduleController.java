package org.zerock.scheduleapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.scheduleapp.dto.ScheduleDeleteDTO;
import org.zerock.scheduleapp.dto.ScheduleRequestDTO;
import org.zerock.scheduleapp.dto.ScheduleResponseDTO;
import org.zerock.scheduleapp.dto.ScheduleUpdateDTO;
import org.zerock.scheduleapp.entity.Schedule;
import org.zerock.scheduleapp.exception.LoginException;
import org.zerock.scheduleapp.service.ScheduleService;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping("/schedules")
    public ScheduleResponseDTO addSchedule(@RequestBody ScheduleRequestDTO schedule) {
        return service.addSchedule(schedule);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDTO getSchedule(@PathVariable Long id) {
        return service.viewScheduleById(id);
    }

    @PatchMapping("/schedules/{id}")
    public ScheduleResponseDTO updateSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateDTO updateDTO) {
        if (service.checkValidPassword(id, updateDTO.getPassword())) {
            return service.updateSchedule(id, updateDTO);
        } else {
            throw new LoginException("Wrong Password");
        }
    }

    @DeleteMapping("/schedules/{id}")
    public void deleteSchedule(@PathVariable Long id, @RequestBody ScheduleDeleteDTO deleteDTO) {
        if (service.checkValidPassword(id, deleteDTO.getPassword())) {
            service.deleteSchedule(id);
        } else {
            throw new LoginException("Wrong Password");
        }
    }
}
