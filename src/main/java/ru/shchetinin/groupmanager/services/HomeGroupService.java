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
import ru.shchetinin.groupmanager.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeGroupService {

    private final UserRepository userRepo;
    private final GroupRepository groupRepository;

    public ResponseEntity<List<Group>> getGroups(Principal principal){
        User user = userRepo.findByUsername(principal.getName());
        if (user != null){
            return ResponseEntity.ok(user.getGroups());
        }
        throw new UserNotFoundException();
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
                user,
                new ArrayList<>());
        group.getMembers().add(user);
        groupRepository.save(group);
    }

    public void deleteGroup(UUID id, Principal principal){
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
