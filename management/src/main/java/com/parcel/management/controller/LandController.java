package com.parcel.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/land")
@RequiredArgsConstructor
public class LandController {
    private final LandService landService;

    @PostMapping("/registerLand")
    public ResponseEntity<LandResponseDto> register(@Valid @RequestBody LandRequestDto landRequestDto){
        return ResponseEntity.ok(landService.registerLand(landRequestDto));
    }

    @GetMapping("/getLand/{id}")
    public ResponseEntity<LandResponseDto> getLand(@PathVariable Long id){
        return ResponseEntity.ok(landService.getLand(id));
    }

    @GetMapping("/getAllLands")
    public ResponseEntity<List<LandResponseDto>> getAllLands(){
        return ResponseEntity.ok(landService.getAllLands());
    }

    @PutMapping("/updateLand/{id}")
    public ResponseEntity<LandResponseDto> updateLand(@RequestBody LandRequestDto landRequestDto,@PathVariable Long id){
        return ResponseEntity.ok(landService.updateLand(id, landRequestDto));
    }

    @DeleteMapping("/deleteLand/{id}")
    public ResponseEntity<Void> deleteLand(@PathVariable Long id){
        landService.deleteLand(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<LandResponseDto>> findByLocation(@RequestParam double longitude,@RequestParam double latitude){
        return ResponseEntity.ok(landService.findLandByLocation(longitude,latitude));
    }

}
