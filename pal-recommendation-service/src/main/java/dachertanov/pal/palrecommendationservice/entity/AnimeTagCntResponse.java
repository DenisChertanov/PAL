package dachertanov.pal.palrecommendationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimeTagCntResponse {
    private UUID animeTagId;
    private Long cnt;
}
