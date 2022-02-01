package dachertanov.pal.palbackendservice.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Anime {
    @Id
    @Column(name = "anime_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID animeId;

    private String imageUrl;

    @NotNull
    private String title;

    @NotNull
    @Min(0)
    @Max(10)
    private Double mark = 0.0;

    @NotNull
    private Integer year;

    @NotNull
    private String studio;

    @NotNull
    private String director;

    @NotNull
    @Min(0)
    private Integer episodes = 0;

    private String description;

    @NotNull
    @Min(0)
    private Double duration = 0.0;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "anime_state_id", referencedColumnName = "anime_state_id")
    private AnimeState animeState;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "anime_type_id", referencedColumnName = "anime_type_id")
    private AnimeType animeType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "anime_tag_mapper",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_tag_id")
    )
    private List<AnimeTag> animeTags;
}
