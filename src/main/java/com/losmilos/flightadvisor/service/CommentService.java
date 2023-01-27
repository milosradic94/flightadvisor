package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.Comment;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.request.AddCommentRequest;
import com.losmilos.flightadvisor.model.dto.request.UpdateCommentRequest;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.model.mapper.CommentMapperImpl;
import com.losmilos.flightadvisor.repository.CityRepository;
import com.losmilos.flightadvisor.repository.CommentRepository;
import com.losmilos.flightadvisor.utility.Constants.CacheNames;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final CityRepository cityRepository;

    private final CommentMapperImpl commentMapper;

    private final CityMapperImpl cityMapper;

    @CacheEvict(value = CacheNames.CITIES_WITH_COMMENTS, allEntries = true)
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

    @Transactional
    @CacheEvict(value = CacheNames.CITIES_WITH_COMMENTS, allEntries = true)
    public void deleteByIdAndUser(Long id, User user) {
        commentRepository.deleteByIdAndUserId(id, user.getId());
    }
}
