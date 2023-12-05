package ru.shchetinin.groupmanager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shchetinin.groupmanager.dto.GroupDto;
import ru.shchetinin.groupmanager.entities.Group;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.exceptions.GroupAlreadyExistException;
import ru.shchetinin.groupmanager.exceptions.NotFoundGroupDeleteException;
import ru.shchetinin.groupmanager.exceptions.NotRealCreatorException;
import ru.shchetinin.groupmanager.exceptions.UserNotFoundException;
import ru.shchetinin.groupmanager.repositories.GroupRepository;
import ru.shchetinin.groupmanager.repositories.JoinedUserGroupRepository;
import ru.shchetinin.groupmanager.repositories.UserRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static ru.shchetinin.groupmanager.util.Checker.isRealCreator;
import static ru.shchetinin.groupmanager.util.Checker.isUserExist;

@Service
@RequiredArgsConstructor
public class HomeGroupService {

    private final UserRepository userRepo;
    private final GroupRepository groupRepository;
    private final JoinedUserGroupRepository joinedUserGroupRepository;

    public ResponseEntity<List<GroupDto>> getGroups(Principal principal){
        User user = userRepo.findByUsername(principal.getName());
        isUserExist(user);
        System.out.println(joinedUserGroupRepository.findByUser(user));
        return ResponseEntity.ok(
                user.getGroups()
                        .stream()
                        .map(
                                group -> {
                                    return new GroupDto(group.getId(), group.getName(), group.getDescription());
                                }

                        )
                        .collect(Collectors.toList())
        );
    }

    public void createNewGroup(GroupDto groupDto, Principal principal){
        User user = userRepo.findByUsername(principal.getName());
        Optional<Group> groupInBase = groupRepository.findById(groupDto.getId());
        if (groupInBase.isPresent()){
            throw new GroupAlreadyExistException();
        }
        Group group = new Group(
                groupDto.getId(),
                groupDto.getName(),
                groupDto.getDescription(),
                user);
        group.getMembers().add(user);
        groupRepository.save(group);
    }

    public void deleteGroup(UUID id, Principal principal){
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()){
            isRealCreator(group.get(), principal);
            groupRepository.delete(group.get());
        } else {
            throw new NotFoundGroupDeleteException();
        }
    }

}
