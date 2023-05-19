package com.example.brandboostclient.model;

import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public Long id;
    public String username;
    public String password;
    public Set<Role> roles;

}
