package ru.shchetinin.groupmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.groupmanager.dto.UserDto;
import ru.shchetinin.groupmanager.entities.Group;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.exceptions.NotFoundGroupException;
import ru.shchetinin.groupmanager.exceptions.UserNotFoundException;
import ru.shchetinin.groupmanager.repositories.GroupRepository;
import ru.shchetinin.groupmanager.repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.shchetinin.groupmanager.util.Checker.*;

@RestController
@RequestMapping("/home/groups/{groupId}/members")
@RequiredArgsConstructor
public class HomeGroupMembersController {

    private final UserRepository userRepo;
    private final GroupRepository groupRepository;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsersFromGroup(@PathVariable UUID groupId,
                                                           Principal principal){
        Optional<Group> group = groupRepository.findById(groupId);

        isGroupExist(group);
        isRealCreator(group.get(), principal);

        return ResponseEntity.ok(group.get().getMembers()
                .stream()
                .map(user -> new UserDto(user.getUsername()))
                .collect(Collectors.toList()));

    }
    /**
     * Админ добавляет юзера
     * */
    @PostMapping("/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addUserInGroup(@PathVariable UUID groupId,
                               @PathVariable String userId,
                               Principal principal){
        Optional<Group> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userId);

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        group.get().getMembers().add(user);
        groupRepository.save(group.get());

    }

    @PostMapping("/delete/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserFromGroup(@PathVariable UUID groupId,
                               @PathVariable String userId,
                               Principal principal){
        Optional<Group> group = groupRepository.findById(groupId);
        User user = userRepo.findByUsername(userId);

        isUserExist(user);
        isGroupExist(group);
        isRealCreator(group.get(), principal);

        group.get().getMembers().remove(user);
        groupRepository.save(group.get());

    }

    /**
     * admin: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5ydSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiZXhwIjoxNzAxNjg1MjEyLCJpYXQiOjE3MDE2ODM0MTJ9.nIWOyihF2lGPlq53zTNpQLlsJNefZ8vEzftNycxfhp0
     * user: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGFzLnNoY0BnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNzAxNjg1Mjc3LCJpYXQiOjE3MDE2ODM0Nzd9.ukfdgK9U3dyGR4UhRLEpz0D0H2AsFwJfpj3nTlMEyo0
     * group: a95d3414-921a-11ee-b9d1-0242ac120002
     * */

}
