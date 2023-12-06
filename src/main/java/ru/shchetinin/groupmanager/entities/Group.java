package ru.shchetinin.groupmanager.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "groups_e")
@AllArgsConstructor
@NoArgsConstructor
public class Group implements Comparable<Group>{

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
    private Set<User> members = new TreeSet<>();

    public Group(UUID id, String name, String description, User owner){
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    @Override
    public int compareTo(Group o) {
        return id.compareTo(o.id);
    }
}
