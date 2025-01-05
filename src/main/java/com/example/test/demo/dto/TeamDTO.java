package com.example.test.demo.dto;


import com.example.test.demo.models.Team;
import jakarta.validation.constraints.*;

public class TeamDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 0, message = "Balance must be positive")
    private double balance;
    @DecimalMin(value = "0.0", message = "Commission rate must be positive")
    @DecimalMax(value = "10.0", message = "Commission rate cannot be more than 10%")
    private double commissionRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }
    public Team toEntity() {
        Team team = new Team();
        team.setName(this.name);
        team.setBalance(this.balance);
        team.setCommissionRate(this.commissionRate);
        return team;
    }
    public static TeamDTO fromEntity(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setName(team.getName());
        dto.setBalance(team.getBalance());
        dto.setCommissionRate(team.getCommissionRate());
        return dto;
    }
}
