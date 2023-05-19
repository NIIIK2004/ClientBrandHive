package com.example.brandboostclient.response;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class BaseResponse {
    public boolean success;
    public String message;
    public String role;
}
