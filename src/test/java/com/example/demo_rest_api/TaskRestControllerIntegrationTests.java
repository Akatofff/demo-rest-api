package com.example.demo_rest_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TaskRestControllerIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    InMemoryTaskRepository inMemoryTaskRepository;

    @Test
    void handleCreateNewTask_PayloadIsInvalid_ReturnsValidResponseEntity() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/api/tasks");
        this
                .inMemoryTaskRepository.getListOfTasks().addAll(List.of(
                new Task(UUID.fromString("ff623e7b-2ea7-467d-972d-f535429e979c"), "First task", false),
                new Task(UUID.fromString("ce2bde17-ea81-4fbb-b121-a354157076a1"), "Second task", false)));

        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                
                                  {
                                    "id": "ff623e7b-2ea7-467d-972d-f535429e979c",
                                    "details": "First task",
                                    "isComplete": false
                                  }
                               """)
                );
    }

    @Test
    void handleCreateNewTask() {
    }

    @Test
    void handleFindTask() {
    }
}