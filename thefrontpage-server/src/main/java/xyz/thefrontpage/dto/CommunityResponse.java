package xyz.thefrontpage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityResponse {
    private Long id;
    private String name;
    private String description;
    private String username;
}
