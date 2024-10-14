package com.improvement.movieflix.services;


import com.improvement.movieflix.dto.ReviewDTO;
import com.improvement.movieflix.dto.UserDTO;
import com.improvement.movieflix.entities.User;
import com.improvement.movieflix.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byEmail = userRepository.findByEmail(username);
        if (byEmail.isEmpty()) {
            logger.error("User not found{}", username);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("User found {}", username);
        return byEmail.get();
    }

    @Transactional
    public User getReviewAuthor(ReviewDTO reviewDTO){
        return userRepository.findById(reviewDTO.getUser())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


}
