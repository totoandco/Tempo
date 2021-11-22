package com.exercise.tempo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "team_id")
    private UUID teamId;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "roleId")
    private Role role;


    public Member() {
    }

    public Member(UUID teamId, UUID userId, Role role) {
        this.teamId = teamId;
        this.userId = userId;
        this.role = role;
    }
}
