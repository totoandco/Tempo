package com.exercise.tempo.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MemberDTO {
    private UUID userId;
    private UUID teamId;
}
