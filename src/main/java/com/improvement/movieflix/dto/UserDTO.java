package com.improvement.movieflix.dto;

import com.improvement.movieflix.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
    }

}
