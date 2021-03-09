package xyz.thefrontpage.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityInput {
    private String name;
    private String description;
    private String username;
}
