package miaad.sgrh.user.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.dto.UserDto;
import miaad.sgrh.user.entity.User;
import miaad.sgrh.user.exception.RessourceNotFoundException;
import miaad.sgrh.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        try{
            StagiaireDto account = userService.createUser(userDto);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
