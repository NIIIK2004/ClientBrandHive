package com.example.brandboostclient.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    public Long id;
    public String name;
    public String surname;
    public String lastname;
    public String number;
    public String address;
    public String email;

    @Override
    public String toString() {
        return surname + " " + name.charAt(0) + "." + lastname.charAt(0);
    }
}
