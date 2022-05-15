package dachertanov.pal.palbackendservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class AnimePlaylist {
    @Id
    @Column(name = "anime_playlist_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID animePlaylistId;

    @NotNull
    private String title = "Новый плейлист";

    @NotNull
    private String description = "Описание нового плейлиста";

    private String imageUrl;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer mark = 0;

    @NotNull
    private LocalDateTime timeCreated;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "anime_playlist_mapper",
            joinColumns = @JoinColumn(name = "anime_playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id")
    )
    private List<Anime> animeList;
}
