package xyz.thefrontpage.mapper;

import xyz.thefrontpage.dto.UserDto;
import xyz.thefrontpage.entity.User;

public class UserMapper {

    public static UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
