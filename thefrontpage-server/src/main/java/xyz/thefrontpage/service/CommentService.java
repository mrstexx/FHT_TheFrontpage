package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.thefrontpage.repository.CommentRepository;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

}
