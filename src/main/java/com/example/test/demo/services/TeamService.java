package com.example.test.demo.services;


import com.example.test.demo.models.Team;
import com.example.test.demo.repositories.TeamRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepo teamRepository;

    public TeamService(TeamRepo teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }
    public Team updateTeam(Long id, Team updatedTeam) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        team.setName(updatedTeam.getName());
        team.setBalance(updatedTeam.getBalance());
        team.setCommissionRate(updatedTeam.getCommissionRate());
        return teamRepository.save(team);
    }
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));
    }
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

}
