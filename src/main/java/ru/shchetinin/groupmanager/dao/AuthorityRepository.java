package ru.shchetinin.groupmanager.dao;

import org.springframework.data.repository.CrudRepository;
import ru.shchetinin.groupmanager.entities.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, String> {}