package com.improvement.movieflix.resources;

import com.improvement.movieflix.utils.TokenUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MoveResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    String visitorUsername;
    String visitorPassword;

    @BeforeEach
    void setUp() {
        visitorUsername = "bob@gmail.com";
        visitorPassword = "123456";
    }


    @Test
    public void findAllPagedShouldReturnMovieObject () throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, visitorUsername, visitorPassword);
        ResultActions result =
                mockMvc.perform(get("/movies")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].title").value("A Voz do Silêncio"));
        result.andExpect(jsonPath("$.content[0].subTitle").value("Koe no Katachi"));
        result.andExpect(jsonPath("$.content[0].dateYear").value(2016));
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://image.tmdb.org/t/p/w533_and_h300_bestv2/5lAMQMWpXMsirvtLLvW7cJgEPkU.jpg"));
        result.andExpect(jsonPath("$.content[0].genreId").value(3));
    }

    @Test
    public void findAllPagedShouldReturnThrowIsUnauthorized() throws Exception {
        ResultActions result =
                mockMvc.perform(get("/movies")
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnauthorized());
    }

}
