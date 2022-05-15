package dachertanov.pal.palbackendservice.entity;

import dachertanov.pal.palbackendservice.entity.id.AnimePlaylistMapperId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@IdClass(AnimePlaylistMapperId.class)
@AllArgsConstructor
@NoArgsConstructor
public class AnimePlaylistMapper {
    @Id
    @Column(name = "anime_playlist_id")
    private UUID animePlaylistId;

    @Id
    @Column(name = "anime_id")
    private UUID animeId;

    @NotNull
    private Integer sequence = 0;
}
