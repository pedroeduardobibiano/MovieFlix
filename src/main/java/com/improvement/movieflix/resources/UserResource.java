package com.improvement.movieflix.resources;

import com.improvement.movieflix.dto.UserDTO;
import com.improvement.movieflix.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserResource {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> FindAllPaged() {
        List<UserDTO> dto = userService.findAll();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
