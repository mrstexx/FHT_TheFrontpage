package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.thefrontpage.repository.PostRepository;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

}
