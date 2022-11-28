package com.losmilos.flightadvisor.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.domain.Comment;
import com.losmilos.flightadvisor.model.domain.Role;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.dto.request.AddCommentRequest;
import com.losmilos.flightadvisor.model.dto.request.UpdateCommentRequest;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.model.mapper.CommentMapperImpl;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import com.losmilos.flightadvisor.model.persistance.RoleEntity;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import com.losmilos.flightadvisor.repository.CityRepository;
import com.losmilos.flightadvisor.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CommentMapperImpl commentMapper;

    @Mock
    private CityMapperImpl cityMapper;

    @InjectMocks
    private CommentService commentService;

    private static final Role ROLE = Role.builder()
            .id(1l)
            .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
            .build();

    private static final User USER = User.builder()
            .id(1l)
            .username("DummyUsername")
            .password("DummyPassword")
            .role(ROLE)
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(ROLE.getRole().name())))
            .build();

    private static final RoleEntity ROLE_ENTITY = RoleEntity.builder()
            .id(1l)
            .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
            .build();

    private static final UserEntity USER_ENTITY = UserEntity.builder()
            .id(1l)
            .firstName("DummyFirstName")
            .lastName("DummyLastName")
            .username("DummyUsername")
            .password("DummyPassword")
            .role(ROLE_ENTITY)
            .build();

    private static final AddCommentRequest ADD_COMMENT_REQUEST = AddCommentRequest.builder()
            .description("DummyDescription")
            .cityId(1l)
            .build();

    private static final City CITY = City.builder()
            .id(1l)
            .name("DummyName")
            .country("DummyCountry")
            .description("DummyDescription")
            .build();

    private static final CityEntity CITY_ENTITY = CityEntity.builder()
            .id(1l)
            .name("DummyName")
            .country("DummyCountry")
            .description("DummyDescription")
            .build();

    private static final Comment COMMENT = Comment.builder()
            .id(1l)
            .description("DummyDescription")
            .city(CITY)
            .user(USER)
            .build();

    private static final CommentEntity COMMENT_ENTITY = CommentEntity.builder()
            .id(1l)
            .description("DummyDescription")
            .city(CITY_ENTITY)
            .user(USER_ENTITY)
            .build();

    private static final UpdateCommentRequest UPDATE_COMMENT_REQUEST = UpdateCommentRequest.builder()
            .id(1l)
            .description("DummyDescription")
            .cityId(1l)
            .build();

    @Test
    void addComment_ShouldThrowException_WhenCityDoesntExist() {
        when(cityRepository.findById(ADD_COMMENT_REQUEST.getCityId())).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> commentService.addComment(ADD_COMMENT_REQUEST, USER));

        Assertions.assertEquals(notFoundException.getMessage(), "City Not Found!");
    }

    @Test
    void addComment_ShouldReturnCommentDomain_WhenCommentIsSuccessfullyCreated() {
        when(cityRepository.findById(ADD_COMMENT_REQUEST.getCityId())).thenReturn(Optional.of(CITY_ENTITY));
        when(cityMapper.entityToDomain(CITY_ENTITY)).thenReturn(CITY);
        when(commentMapper.domainToEntity(any(COMMENT.getClass()))).thenReturn(COMMENT_ENTITY);
        when(commentRepository.save(COMMENT_ENTITY)).thenReturn(COMMENT_ENTITY);
        when(commentMapper.entityToDomain(COMMENT_ENTITY)).thenReturn(COMMENT);

        final var comment = commentService.addComment(ADD_COMMENT_REQUEST, USER);

        Assertions.assertEquals(comment, COMMENT);
    }

    @Test
    void updateComment_ShouldThrowException_WhenCityDoesntExist() {
        when(cityRepository.findById(UPDATE_COMMENT_REQUEST.getCityId())).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> commentService.updateComment(UPDATE_COMMENT_REQUEST, USER));

        Assertions.assertEquals(notFoundException.getMessage(), "City Not Found!");
    }

    @Test
    void updateComment_ShouldThrowException_WhenCommentDoesntExist() {
        when(cityRepository.findById(ADD_COMMENT_REQUEST.getCityId())).thenReturn(Optional.of(CITY_ENTITY));
        when(commentRepository.findByIdAndUserId(UPDATE_COMMENT_REQUEST.getId(), USER.getId())).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> commentService.updateComment(UPDATE_COMMENT_REQUEST, USER));

        Assertions.assertEquals(notFoundException.getMessage(), "Comment Not Found!");
    }

    @Test
    void updateComment_ShouldReturnCommentDomain_WhenCommentIsSuccessfullyEdited() {
        when(cityRepository.findById(UPDATE_COMMENT_REQUEST.getCityId())).thenReturn(Optional.of(CITY_ENTITY));
        when(commentRepository.findByIdAndUserId(UPDATE_COMMENT_REQUEST.getCityId(), USER.getId())).thenReturn(Optional.of(COMMENT_ENTITY));
        when(commentRepository.save(COMMENT_ENTITY)).thenReturn(COMMENT_ENTITY);
        when(commentMapper.entityToDomain(COMMENT_ENTITY)).thenReturn(COMMENT);

        final var comment = commentService.updateComment(UPDATE_COMMENT_REQUEST, USER);

        Assertions.assertEquals(comment, COMMENT);
    }





    @Test
    void deleteByIdAndUser_ShouldThrowException_WhenCommentDoesntExist() {
        when(commentRepository.findByIdAndUserId(1l, USER.getId())).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.assertThrows(NotFoundException.class, () -> commentService.deleteByIdAndUser(1l, USER));

        Assertions.assertEquals(notFoundException.getMessage(), "Comment Not Found!");
    }

    @Test
    void deleteByIdAndUser_ShouldExecuteDelete_WhenCommentIsSuccessfullyDeleted() {
        when(commentRepository.findByIdAndUserId(1l, USER.getId())).thenReturn(Optional.of(COMMENT_ENTITY));

        commentService.deleteByIdAndUser(1l, USER);

        verify(commentRepository).delete(COMMENT_ENTITY);
    }
}
