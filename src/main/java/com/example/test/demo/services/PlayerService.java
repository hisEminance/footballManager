package com.example.test.demo.services;


import com.example.test.demo.models.Player;
import com.example.test.demo.models.Team;
import com.example.test.demo.repositories.PlayerRepo;
import com.example.test.demo.repositories.TeamRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepo playerRepository;
    private final TeamRepo teamRepository;

    public PlayerService(PlayerRepo playerRepository,  TeamRepo teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
    public Player updatePlayer(Long id, Player updatedPlayer) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
        player.setName(updatedPlayer.getName());
        player.setAge(updatedPlayer.getAge());
        player.setExperienceMonths(updatedPlayer.getExperienceMonths());
        player.setTeam(updatedPlayer.getTeam());
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    //Transfer
    @Transactional
    public void transferPlayerToAnotherTeam(Long playerId, Long toTeamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));
        Team fromTeam = player.getTeam();
        Team toTeam = teamRepository.findById(toTeamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        double transferCost = player.getExperienceMonths() * 100000.0 / player.getAge();
        double commission = transferCost * (toTeam.getCommissionRate() / 100.0);
        double totalCost = transferCost + commission;

        if (toTeam.getBalance() < totalCost) {
            throw new IllegalArgumentException("Insufficient balance for transfer");
        }

        toTeam.setBalance(toTeam.getBalance() - totalCost);
        fromTeam.setBalance(fromTeam.getBalance() + totalCost);
        player.setTeam(toTeam);

        playerRepository.save(player);
        teamRepository.save(fromTeam);
        teamRepository.save(toTeam);
    }
}
