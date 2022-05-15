package dachertanov.pal.palbackendservice.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimePlaylistMapperId implements Serializable {
    private UUID animePlaylistId;
    private UUID animeId;
}
