package xyz.thefrontpage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String username;
    private String communityName;
    private String title;
    private String url;
    private String body;
    private LocalDateTime createdAt;
    private Integer voteCount;
    private Integer commentCount;
}
