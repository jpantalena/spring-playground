package com.example.demo.acceptance;

import com.example.demo.model.MovieResponse;
import com.example.demo.model.OmdbMovie;
import com.example.demo.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringMovieTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testMovies() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        MockHttpServletRequestBuilder request = get("/movies")
                .param("q", "harry")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();

        MovieResponse movieResponse = MovieResponse.builder()
                .search(asList(OmdbMovie.builder()
                        .title("movie")
                        .imdbID("imbdId")
                        .poster("poster")
                        .year("2018")
                        .type("Movie")
                        .build()))
                .build();

        mockServer
                .expect(requestTo("http://www.omdbapi.com?s=harry&apikey=37bd901e"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(objectMapper.writeValueAsString(movieResponse), MediaType.APPLICATION_JSON));

        this.mvc.perform(request).andExpect(status().isOk());

        mockServer.verify();
    }
}
