package ru.shchetinin.groupmanager.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Setter
@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Comparable<User>{

    @Id
    private String username;
    private String password;
    private String activationCode;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "members")
    private Set<Group> groups = new TreeSet<>();

    public User(String username, String password, String activationCode, boolean enabled) {
        this.username = username;
        this.password = password;
        this.activationCode = activationCode;
        this.enabled = enabled;
    }

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.getUsername());
    }
}
