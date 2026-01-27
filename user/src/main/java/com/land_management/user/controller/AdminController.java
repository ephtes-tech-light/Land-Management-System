package com.land_management.user.controller;

import com.land_management.user.dto.UserResponseDto;
import com.land_management.user.model.UserUpdateRequest;
import com.land_management.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;


    @PutMapping("/reject/{id}")
    public ResponseEntity<String> reject(@PathVariable Long id){
        return ResponseEntity.ok(adminService.rejectUpdateRequest(id));
    }

    @GetMapping("/listPending")
    public ResponseEntity<List<UserUpdateRequest>> pendings(){
        return ResponseEntity.ok(adminService.getPending());
    }

    @PutMapping("/approveUserUpdate/{id}")
    public ResponseEntity<UserResponseDto> approve(@PathVariable Long id){
        return ResponseEntity.ok(adminService.approveUserUpdate(id));
    }

    @PutMapping("/approveUser/{id}")
    public ResponseEntity<String> approveUser(@PathVariable Long id){
        return new ResponseEntity<>(adminService.approveUser(id), HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserResponseDto>> allUser(){

        return ResponseEntity.ok(adminService.getAllUser());
    }


}
