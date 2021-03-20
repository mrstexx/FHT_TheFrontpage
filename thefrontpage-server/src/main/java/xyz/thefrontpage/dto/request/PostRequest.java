package xyz.thefrontpage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long id;
    private String communityName;
    private String title;
    private String url;
    private String body;
}
