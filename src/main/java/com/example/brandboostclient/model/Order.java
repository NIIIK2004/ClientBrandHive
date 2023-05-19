package com.example.brandboostclient.model;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    public Long id;
    public Client client;
    public Services service;
    public String orderDate;
    public String finalDate;
}
