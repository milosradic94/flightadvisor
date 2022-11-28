package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.Comment;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.request.AddCommentRequest;
import com.losmilos.flightadvisor.model.dto.request.UpdateCommentRequest;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.model.mapper.CommentMapperImpl;
import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import com.losmilos.flightadvisor.repository.CityRepository;
import com.losmilos.flightadvisor.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final CityRepository cityRepository;

    private final CommentMapperImpl commentMapper;

    private final CityMapperImpl cityMapper;

    public Comment addComment(AddCommentRequest addCommentRequest, User user) {
        final var city =
                cityRepository.findById(addCommentRequest.getCityId())
                        .map(cityMapper::entityToDomain)
                        .orElseThrow(() -> new NotFoundException("City Not Found!"));

        final var comment =
                Comment.builder()
                        .city(city)
                        .user(user)
                        .description(addCommentRequest.getDescription())
                        .build();

        return commentMapper.entityToDomain(commentRepository.save(commentMapper.domainToEntity(comment)));
    }

    public Comment updateComment(UpdateCommentRequest updateCommentRequest, User user) {
        final var city =
                cityRepository.findById(updateCommentRequest.getCityId())
                        .orElseThrow(() -> new NotFoundException("City Not Found!"));

        final var commentEntity =
                commentRepository.findByIdAndUserId(updateCommentRequest.getId(), user.getId())
                        .orElseThrow(() -> new NotFoundException("Comment Not Found!"));;

        commentEntity.setCity(city);
        commentEntity.setDescription(updateCommentRequest.getDescription());

        return commentMapper.entityToDomain(commentRepository.save(commentEntity));
    }

    public void deleteByIdAndUser(Long id, User user) {
        final var commentEntity =
                commentRepository.findByIdAndUserId(id, user.getId())
                        .orElseThrow(() -> new NotFoundException("Comment Not Found!"));

        commentRepository.delete(commentEntity);
    }
}
