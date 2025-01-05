package com.example.test.demo.services;


import com.example.test.demo.models.Player;
import com.example.test.demo.models.Team;
import com.example.test.demo.repositories.PlayerRepo;
import com.example.test.demo.repositories.TeamRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataBaseInitializer {
    @Autowired
    private TeamRepo teamRepository;
    @Autowired
    private PlayerRepo playerRepository;

    @PostConstruct
    public void init() {
        Team team = new Team();
        team.setName("Команда А");
        teamRepository.save(team);

        Player player = new Player();
        player.setName("John Doe");
        player.setAge(25);
        player.setExperienceMonths(36);
        player.setTeam(team);
        playerRepository.save(player);
    }
}
