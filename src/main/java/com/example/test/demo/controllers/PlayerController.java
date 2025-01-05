package com.example.test.demo.controllers;


import com.example.test.demo.dto.PlayerDTO;
import com.example.test.demo.models.Player;
import com.example.test.demo.models.Team;
import com.example.test.demo.services.PlayerService;
import com.example.test.demo.services.TeamService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    private final TeamService teamService;


    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }



    @GetMapping
    public List<PlayerDTO> getAllPlayers() {
        return playerService.findAllPlayers()
                .stream()
                .map(PlayerDTO::fromEntity)
                .toList();
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        Team team = teamService.getTeamById(playerDTO.getTeamId());
        Player player = playerDTO.toEntity(team);
        Player savedPlayer = playerService.savePlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlayerDTO.fromEntity(savedPlayer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        Team team = teamService.getTeamById(playerDTO.getTeamId());
        Player playerToUpdate = playerDTO.toEntity(team);
        Player updatedPlayer = playerService.updatePlayer(id, playerToUpdate);
        return ResponseEntity.ok(PlayerDTO.fromEntity(updatedPlayer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{playerId}/transfer/{toTeamId}")
    public ResponseEntity<String> transferPlayer(@PathVariable Long playerId, @PathVariable Long toTeamId) {
        playerService.transferPlayerToAnotherTeam(playerId, toTeamId);
        return ResponseEntity.ok("Player transferred successfully");
    }
}
