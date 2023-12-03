package ru.shchetinin.groupmanager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "groups_e")
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    private UUID id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_name", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "users_groups",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_name") })
    private List<User> members;
}