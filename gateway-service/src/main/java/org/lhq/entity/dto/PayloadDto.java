package org.lhq.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PayloadDto {
    private String sub;
    private Long iat;
    private Long exp;
    private String jti;
    private Long userId;
    private String username;
    private List<String> authorities;
}
