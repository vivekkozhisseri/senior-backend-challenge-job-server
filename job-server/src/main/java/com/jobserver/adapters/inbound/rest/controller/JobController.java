package com.jobserver.adapters.inbound.rest.controller;

import com.jobserver.adapters.inbound.rest.dto.CreateJobRequest;
import com.jobserver.adapters.inbound.rest.dto.JobResponse;
import com.jobserver.domain.entity.Job;
import com.jobserver.ports.inbound.JobUseCases;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Jobs", description = "Job management APIs")
public class JobController {

    private final JobUseCases jobUseCases;

    public JobController(JobUseCases jobUseCases) {
        this.jobUseCases = jobUseCases;
    }

    @PostMapping
    @Operation(summary = "Submit a job", description = "Creates a new async job and returns immediately")
    public ResponseEntity<JobResponse> submitJob(@Valid @RequestBody CreateJobRequest request) {
        Job job = jobUseCases.submitJob(request.userId(), request.projectId(), request.payload());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(JobResponse.fromDomain(job));
    }

    @GetMapping("/{jobId}")
    @Operation(summary = "Get job status", description = "Returns job status and result if completed")
    public ResponseEntity<JobResponse> getJob(@PathVariable UUID jobId) {
        Job job = jobUseCases.getJob(jobId);
        return ResponseEntity.ok(JobResponse.fromDomain(job));
    }
}
