package com.example.brandboostclient.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Services {
    public Long id;
    public String name;
    public String description;
    public int price;

    @Override
    public String toString() {
        return name;
    }
}
