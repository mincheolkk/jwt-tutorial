package me.prac.tutorial.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    // Token 정보를 Response 할 때 사용할 TokenDto

    private String token;
}
