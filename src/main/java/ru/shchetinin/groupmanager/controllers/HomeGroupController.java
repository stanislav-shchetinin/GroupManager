package ru.shchetinin.groupmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.groupmanager.dto.GroupDto;
import ru.shchetinin.groupmanager.entities.Group;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.exceptions.GroupAlreadyExistException;
import ru.shchetinin.groupmanager.exceptions.NotFoundGroupDeleteException;
import ru.shchetinin.groupmanager.exceptions.NotRealCreatorException;
import ru.shchetinin.groupmanager.repositories.GroupRepository;
import ru.shchetinin.groupmanager.repositories.UserRepository;
import ru.shchetinin.groupmanager.services.HomeGroupService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/home/groups")
@RequiredArgsConstructor
public class HomeGroupController {

    private final HomeGroupService homeGroupService;
    @GetMapping
    public ResponseEntity<List<Group>> getGroups(Principal principal){
        return homeGroupService.getGroups(principal);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createNewGroup(@RequestBody GroupDto groupDto, Principal principal){
        homeGroupService.createNewGroup(groupDto, principal);
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable UUID id, Principal principal){
        homeGroupService.deleteGroup(id, principal);
    }
}
