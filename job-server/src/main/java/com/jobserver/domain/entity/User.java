package com.jobserver.domain.entity;

import java.util.UUID;

public record User(UUID id, String email, String name) {
}

