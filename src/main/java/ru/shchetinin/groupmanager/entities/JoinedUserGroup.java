package ru.shchetinin.groupmanager.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users_groups")
public class JoinedUserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    private Integer numberClasses = 0;
}
