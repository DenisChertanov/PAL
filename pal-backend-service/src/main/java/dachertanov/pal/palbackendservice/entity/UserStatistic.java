package dachertanov.pal.palbackendservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class UserStatistic {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Min(0)
    private Double animeSpentHours = 0.0;

    @NotNull
    @Min(0)
    private Integer animeCount = 0;

    public UserStatistic(UUID userId) {
        this.userId = userId;
    }
}
