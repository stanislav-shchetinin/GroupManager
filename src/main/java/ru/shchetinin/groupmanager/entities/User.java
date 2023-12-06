package ru.shchetinin.groupmanager.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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
    private List<Group> groups = new ArrayList<>();

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
