package com.example.test.demo.dto;

import com.example.test.demo.models.Player;
import com.example.test.demo.models.Team;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PlayerDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 0, message = "Age must be >18")
    private int age;
    private int experienceMonths;
    private Long teamId;
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExperienceMonths() {
        return experienceMonths;
    }

    public void setExperienceMonths(int experienceMonths) {
        this.experienceMonths = experienceMonths;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    public Player toEntity(Team team) {
        Player player = new Player();
        player.setName(this.name);
        player.setAge(this.age);
        player.setExperienceMonths(this.experienceMonths);
        player.setTeam(team); //this sh*t need to be written in other class, google and gpt says smth about TeamMapper but Idk exactly, actually just leave there ``TODO``
        return player;
    }
    public static PlayerDTO fromEntity (Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.getName());
        playerDTO.setAge(player.getAge());
        playerDTO.setExperienceMonths(player.getExperienceMonths());
        playerDTO.setTeamId(player.getTeam().getId());
        return playerDTO;
    }
}
