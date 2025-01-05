package com.example.test.demo.controllers;



import com.example.test.demo.dto.TeamDTO;
import com.example.test.demo.models.Team;
import com.example.test.demo.services.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamDTO> getAllTeams() {
        return teamService.getAllTeams()
                .stream()
                .map(TeamDTO::fromEntity)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Validated TeamDTO teamDTO) {
        Team savedTeam = teamService.saveTeam(teamDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(TeamDTO.fromEntity(savedTeam));
    }

    @PutMapping("/{id}")
    public TeamDTO updateTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO) {
        Team updatedTeam = teamService.updateTeam(id, teamDTO.toEntity());
        return TeamDTO.fromEntity(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
