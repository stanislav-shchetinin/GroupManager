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
import ru.shchetinin.groupmanager.responses.Response;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/home/groups")
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepo;
    private final GroupRepository groupRepository;
    @GetMapping
    public String groups(Principal principal){
        User user = userRepo.findByUsername(principal.getName());
        if (user != null){
            String ret = "{";
            for (Group group : user.getGroups()){
                ret += group.getName() + ",";
            }
            ret += "}";
            return ret;
        }
        return "Nooooo...";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createNewGroup(@RequestBody GroupDto groupDto, Principal principal){
        User user = userRepo.findByUsername(principal.getName());
        Optional<Group> groupInBase = groupRepository.findById(groupDto.getId());
        if (groupInBase.isPresent()){
            throw new GroupAlreadyExistException();
        }
        Group group = new Group(
                groupDto.getId(),
                groupDto.getName(),
                groupDto.getDescription(),
                user,
                new ArrayList<>());
        group.getMembers().add(user);
        groupRepository.save(group);
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable UUID id, Principal principal){
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()){
            String nameRealCreator = group.get().getOwner().getUsername();
            String nameCheckCreator = principal.getName();
            boolean isRealCreator = nameRealCreator.equals(nameCheckCreator);
            if (!isRealCreator){
                throw new NotRealCreatorException();
            }
            groupRepository.delete(group.get());
        } else {
            throw new NotFoundGroupDeleteException();
        }
    }
}
