package org.zerock.scheduleapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.scheduleapp.dto.ReplyRequestDTO;
import org.zerock.scheduleapp.dto.ReplyResponseDTO;
import org.zerock.scheduleapp.exception.RepliesLimitException;
import org.zerock.scheduleapp.repository.ReplyRepo;
import org.zerock.scheduleapp.service.ReplyService;

import java.net.URI;

@RestController
@AllArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/replies/{id}")
    public ResponseEntity<ReplyResponseDTO> addReply(@PathVariable Long id, @RequestBody @Validated ReplyRequestDTO replyRequestDTO) {
        if (replyService.isPossibleToAddReply(id)) {
            ReplyResponseDTO replyResponseDTO = replyService.addReply(replyRequestDTO, id);
            return ResponseEntity.created(URI.create("/replies/" + id)).body(replyResponseDTO);
        } else {
            throw new RepliesLimitException("댓글은 10개까지만 작성할 수 있습니다.");
        }
    }
}
