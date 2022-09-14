package io.spring.com.controller;

import io.spring.com.entity.Response;
import io.spring.com.entity.User;
import io.spring.com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAll(){
        return ResponseEntity.ok(
                Response.builder()
                        .dateTime(LocalDateTime.now())
                        .message("all users succesfuly fetched")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("users", userService.getAll()))
                        .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getOne(@PathVariable("id") int id){
        User user = userService.getOne(id);
        if(user != null) {
            return ResponseEntity.ok(
                    Response.builder()
                            .dateTime(LocalDateTime.now())
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message(String.format("User id: %s's has succefully fetched", user.getId()))
                            .data(Map.of("user", user))
                            .build()
            );
        }

        else {
            return ResponseEntity.ok(
                    Response.builder()
                            .dateTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(String.format("User id: %s's not found", user.getId()))
                            .build());
        }

    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveUser(@RequestBody User user){
        System.out.println(user);

        if(userService.save(user)){
            return ResponseEntity.ok(
                    Response.builder()
                            .dateTime(LocalDateTime.now())
                            .status(HttpStatus.ACCEPTED)
                            .statusCode(HttpStatus.OK.value())
                            .message(String.format("User id: %s's has succefully saved", user.getUsername()))
                            .data(Map.of("user", user))
                            .build()
            );
        }

        else {
            return ResponseEntity.ok(
                    Response.builder()
                            .dateTime(LocalDateTime.now())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message(String.format("User id: %s's not saved", user.getUsername()))
                            .build());
        }

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> editUser(@RequestBody User user, @PathVariable("id") int id){

        if(userService.update(user, id)){
            return ResponseEntity.ok(
                    Response.builder()
                            .dateTime(LocalDateTime.now())
                            .status(HttpStatus.ACCEPTED)
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .message(String.format("User id: %s's edited ", user.getUsername()))
                            .build());
        }

        else {
            return ResponseEntity.ok(
                    Response.builder()
                            .dateTime(LocalDateTime.now())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message(String.format("User id: %s's not edited", id))
                            .build());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") int id){

        if(userService.delete(id)) {
            return ResponseEntity.ok(
                    Response.builder()
                            .dateTime(LocalDateTime.now())
                            .status(HttpStatus.ACCEPTED)
                            .statusCode(HttpStatus.ACCEPTED.value())
                            .message(String.format("User id: %s's deleted", id))
                            .build());
        }

        else {
            return ResponseEntity.ok(
                    Response.builder()
                            .dateTime(LocalDateTime.now())
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(String.format("User id: %s not found", id))
                            .build());
        }
    }

















}
