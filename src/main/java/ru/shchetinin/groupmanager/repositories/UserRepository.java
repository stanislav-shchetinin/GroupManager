package ru.shchetinin.groupmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shchetinin.groupmanager.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
