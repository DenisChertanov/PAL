package dachertanov.pal.palbackenddto.search.in;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Filter {
    private List<UUID> includeGenres;
    private List<UUID> excludeGenres;
    private List<UUID> includeTypes;
    private List<UUID> includeStates;
    private Integer yearFrom = 1800;
    private Integer yearTo = 2100;
    private UUID sortsBy;
    private boolean excludeWatched = false;
}
