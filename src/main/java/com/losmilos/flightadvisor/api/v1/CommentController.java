package com.losmilos.flightadvisor.api.v1;

import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.request.AddCommentRequest;
import com.losmilos.flightadvisor.model.dto.request.UpdateCommentRequest;
import com.losmilos.flightadvisor.model.dto.response.CommentDescriptionResponse;
import com.losmilos.flightadvisor.model.dto.response.CommentResponse;
import com.losmilos.flightadvisor.model.mapper.CommentMapperImpl;
import com.losmilos.flightadvisor.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
@Tag(name = "Comments", description = "Endpoints for comments")
public class CommentController {

    private final CommentService commentService;

    private final CommentMapperImpl commentMapper;

    @PostMapping
    @Operation(summary = "Create comment")
    @ApiResponse(
        responseCode = "201",
        description = "Comment is created",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = CommentResponse.class
            )
        )
    )
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody AddCommentRequest addCommentRequest, @AuthenticationPrincipal User user) {
        final var comment = commentService.addComment(addCommentRequest, user);

        return new ResponseEntity<CommentResponse>(commentMapper.domainToDto(comment), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Update comment")
    @ApiResponse(
        responseCode = "200",
        description = "Comment is updated",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = CommentResponse.class
            )
        )
    )
    public ResponseEntity<CommentResponse> updateComment(@Valid @RequestBody UpdateCommentRequest updateCommentRequest, @AuthenticationPrincipal User user) {
        final var comment = commentService.updateComment(updateCommentRequest, user);

        return ResponseEntity.ok(commentMapper.domainToDto(comment));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete comment")
    @ApiResponse(
        responseCode = "204",
        description = "Comment is deleted"
    )
    ResponseEntity<Void> deleteComment(@PathVariable Long id, @AuthenticationPrincipal User user) {
        commentService.deleteByIdAndUser(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment description")
    @ApiResponse(
        responseCode = "200",
        description = "Returns comment description",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = CommentDescriptionResponse.class
            )
        )
    )
    ResponseEntity<CommentDescriptionResponse> getCommentDescription(@PathVariable Long id, @AuthenticationPrincipal User user) {
        final var commentView = commentService.getCommentDescription(id, user);

        return ResponseEntity.ok(commentMapper.commentViewToCommentDescriptionResponse(commentView));
    }
}
