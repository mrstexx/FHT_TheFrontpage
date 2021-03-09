package xyz.thefrontpage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostInput {
    private String username;
    private String communityName;
    private String title;
    private String url;
    private String body;
}
