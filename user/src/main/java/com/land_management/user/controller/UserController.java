package com.land_management.user.controller;


import com.land_management.user.dto.RegistrationDto;
import com.land_management.user.dto.UpdateUserDto;
import com.land_management.user.dto.UserResponseDto;
import com.land_management.user.model.UserUpdateRequest;
import com.land_management.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

    @Slf4j
    @RestController
    @RequestMapping("/api/user")
    @RequiredArgsConstructor
    public class UserController {
        private final UserService userService;

        @PostMapping("/register")
        public ResponseEntity<String> register(@Valid @RequestBody RegistrationDto registrationDto){

            userService.register(registrationDto);
            return ResponseEntity.ok("Succesful");
        }

        @GetMapping("/getbyid/{id}")
        public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
            return ResponseEntity.ok(userService.getUserById(id));
        }

        @PostMapping("/updateRequest")
        public ResponseEntity<UserUpdateRequest> updateProfile(@RequestBody UpdateUserDto dto){
            return ResponseEntity.ok(userService.updateUserDto(dto));
        }



        @PutMapping("/approve/{id}")
        public ResponseEntity<UserResponseDto> approve(@PathVariable Long id){
            return ResponseEntity.ok(userService.approveUserUpdate(id));
        }
        @PutMapping("/rejected/{id}")
        public ResponseEntity<String> reject(@PathVariable Long id){
            return ResponseEntity.ok(userService.rejectUpdateRequest(id));
        }
        @GetMapping("/getUsers")
        public ResponseEntity<List<UserResponseDto>> allUser(){

            log.info("calling get all user");
            return ResponseEntity.ok(userService.gellAllUser());
        }
        @GetMapping("/listPending")
        public ResponseEntity<List<UserUpdateRequest>> pendings(){
            return ResponseEntity.ok(userService.getPending());
        }

    }
