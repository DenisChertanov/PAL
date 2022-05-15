package dachertanov.pal.palbackendservice.entity;

import dachertanov.pal.palbackendservice.entity.id.AnimePlaylistUserMarkId;
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
@IdClass(AnimePlaylistUserMarkId.class)
@AllArgsConstructor
@NoArgsConstructor
public class AnimePlaylistUserMark {
    @Id
    @Column(name = "anime_playlist_id")
    private UUID animePlaylistId;

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    private Integer mark = 0;
}
