package ru.shchetinin.groupmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shchetinin.groupmanager.dto.UserDto;
import ru.shchetinin.groupmanager.dto.UserMinusDto;
import ru.shchetinin.groupmanager.dto.UserPlusDto;
import ru.shchetinin.groupmanager.services.HomeGroupMembersService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/home/groups/{groupId}/members")
@RequiredArgsConstructor
public class HomeGroupMembersController {

    private final HomeGroupMembersService homeGroupMembersService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsersFromGroup(@PathVariable UUID groupId,
                                                           Principal principal){
        return homeGroupMembersService.getUsersFromGroup(groupId, principal);
    }
    /**
     * Админ добавляет юзера
     * */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void addUserInGroup(@PathVariable UUID groupId,
                               @RequestParam String userId,
                               Principal principal){
        homeGroupMembersService.addUserInGroup(groupId, userId, principal);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserFromGroup(@PathVariable UUID groupId,
                                    @RequestParam String userId,
                               Principal principal){
        homeGroupMembersService.deleteUserFromGroup(groupId, userId, principal);
    }

    @PostMapping("/numberOfClasses/plus")
    @ResponseStatus(HttpStatus.OK)
    public void plusNumberOfCLasses(@PathVariable UUID groupId,
                                    @RequestBody UserPlusDto userPlusDto,
                                    Principal principal){
        homeGroupMembersService.plusNumberOfCLasses(groupId, userPlusDto, principal);
    }
    @PostMapping("/numberOfClasses/minus")
    @ResponseStatus(HttpStatus.OK)
    public void minusNumberOfCLasses(@PathVariable UUID groupId,
                                    @RequestBody UserMinusDto userMinusDto,
                                    Principal principal){

        homeGroupMembersService.minusNumberOfCLasses(groupId, userMinusDto, principal);
    }

    /**
     * admin: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5ydSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiZXhwIjoxNzAxNzcxMjk3LCJpYXQiOjE3MDE3Njk0OTd9.m8v8Mo0hVB2aQXP2f_c8MpXwiwFziObudx7wG3CQP_8
     * user: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGFzLnNoY0BnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNzAxNzcxMjM4LCJpYXQiOjE3MDE3Njk0Mzh9.2yNXSzarnljJfJEcZKgYkPHC4XNOnUHtjHZyuHQrV9E
     * group: a95d3414-921a-11ee-b9d1-0242ac120002
     * */

}
