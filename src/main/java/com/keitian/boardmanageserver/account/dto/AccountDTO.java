package com.keitian.boardmanageserver.account.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {
    private int id;
    @NotBlank
    private String account;
    @NotBlank
    private String password;
    private int role;
    private String profilePic;
    private String email;
    private Timestamp registerDate;
}
