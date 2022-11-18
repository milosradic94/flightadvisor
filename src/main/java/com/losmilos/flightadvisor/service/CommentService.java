package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.User;
import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import com.losmilos.flightadvisor.repository.CommentRepository;
import com.losmilos.flightadvisor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public CommentEntity addComment(CommentEntity comment, User user) {
        UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User Not Found."));

        comment.setUser(userEntity);

        return commentRepository.save(comment);
    }
}
