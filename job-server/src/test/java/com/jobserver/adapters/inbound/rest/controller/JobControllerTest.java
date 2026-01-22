package com.jobserver.adapters.inbound.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobserver.adapters.inbound.rest.dto.CreateJobRequest;
import com.jobserver.adapters.inbound.rest.exception.GlobalExceptionHandler;
import com.jobserver.domain.entity.Job;
import com.jobserver.domain.exception.JobNotFoundException;
import com.jobserver.ports.inbound.JobUseCases;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @Mock
    private JobUseCases jobUseCases;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final UUID USER_ID = UUID.fromString("a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new JobController(jobUseCases))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void submitJobShouldReturn202() throws Exception {
        Job job = Job.create(USER_ID, null, "payload");
        when(jobUseCases.submitJob(any(), any(), any())).thenReturn(job);

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateJobRequest(USER_ID, null, "payload"))))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.jobId").value(job.getId().toString()))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void getJobShouldReturnJob() throws Exception {
        Job job = Job.create(USER_ID, null, "payload");
        when(jobUseCases.getJob(job.getId())).thenReturn(job);

        mockMvc.perform(get("/jobs/{id}", job.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobId").value(job.getId().toString()));
    }

    @Test
    void getJobShouldReturn404WhenNotFound() throws Exception {
        UUID jobId = UUID.randomUUID();
        when(jobUseCases.getJob(jobId)).thenThrow(new JobNotFoundException(jobId));

        mockMvc.perform(get("/jobs/{id}", jobId))
                .andExpect(status().isNotFound());
    }
}
