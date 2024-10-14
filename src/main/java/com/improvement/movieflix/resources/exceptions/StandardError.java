package com.improvement.movieflix.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardError {

    private Instant timestamp;
    private String error;
    private String message;
    public String path;

}
