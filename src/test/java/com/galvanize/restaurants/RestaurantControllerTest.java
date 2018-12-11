package com.galvanize.restaurants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository repository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Before
    public void beforeEach(){
        repository.deleteAll();
    }

    @After
    public void afterEach(){
        repository.deleteAll();
    }

    @Test
    public void getReturnsEmptyList() throws Exception {
        //Setup

        //Exercise
        final String actual = mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Assert
        assertThat(actual, is("[]"));
    }

    @Test
    public void listReturnsOneRestaurantFromRepository() throws Exception {
        //Setup
        final Restaurant expected = new Restaurant(0, "Fred's Ribs");
        repository.save(expected);

        //Exercise
        final String content = mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final List<Restaurant> actual = OBJECT_MAPPER.readValue(content,new TypeReference<List<Restaurant>>(){});

        //Assert
        System.out.println(expected.getId());
        assertThat(actual, contains(expected));
    }

    @Test
    public void addRestaurantReturnsEmptyListForEmptyString() throws Exception{
        //Setup
        String newRestaurant = "";

        //Exercise & Assert
        // POST
        // status is 400
        // assert message is "Restaurant name cannot be empty!"

        //Exercise
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/restaurants")
                .accept(MediaType.APPLICATION_JSON).content(newRestaurant)
                .contentType(MediaType.APPLICATION_JSON);

        final int actual = mockMvc.perform(requestBuilder)
                .andExpect(status().is(400)).andReturn().getResponse().getStatus();

        //Assert
        assertTrue(400 == actual);

    }

    @Test
    public void addRestaurantReturnsRestaurantListWhenAddingNewRestaurant() throws Exception{
        //Setup
        final Restaurant expected = new Restaurant(Long.MIN_VALUE, "Pizza hut");

        String newRestaurant = "{\"name\":\"Pizza hut\"}";


        //Exercise
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/restaurants")
                .accept(MediaType.APPLICATION_JSON).content(newRestaurant)
                .contentType(MediaType.APPLICATION_JSON);

        final String content = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final Restaurant actual = OBJECT_MAPPER.readValue(content,Restaurant.class);

        //Assert
        assertEquals(actual.getName(), expected.getName());

    }
}
