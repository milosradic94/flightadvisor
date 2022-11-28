package com.losmilos.flightadvisor.api.v1;

import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.request.AddCommentRequest;
import com.losmilos.flightadvisor.model.dto.request.UpdateCommentRequest;
import com.losmilos.flightadvisor.model.dto.response.CommentResponse;
import com.losmilos.flightadvisor.model.mapper.CommentMapperImpl;
import com.losmilos.flightadvisor.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    private final CommentMapperImpl commentMapper;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody AddCommentRequest addCommentRequest, @AuthenticationPrincipal User user) {
        final var comment = commentService.addComment(addCommentRequest, user);

        return new ResponseEntity<CommentResponse>(commentMapper.domainToDto(comment), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommentResponse> updateComment(@Valid @RequestBody UpdateCommentRequest updateCommentRequest, @AuthenticationPrincipal User user) {
        final var comment = commentService.updateComment(updateCommentRequest, user);

        return new ResponseEntity<CommentResponse>(commentMapper.domainToDto(comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal User user) {
        commentService.deleteByIdAndUser(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
