package com.urbancartel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlockedRequest {
    private String reason;
    private String duration;
}
