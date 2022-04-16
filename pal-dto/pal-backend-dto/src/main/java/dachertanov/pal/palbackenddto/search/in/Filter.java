package dachertanov.pal.palbackenddto.search.in;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@Data
public class Filter {
    private List<UUID> includeGenres;
    private List<UUID> excludeGenres;
    private List<UUID> includeTypes;
    private List<UUID> includeStates;
    private String yearFrom;
    private String yearTo;
    private UUID sortsBy;
    private boolean excludeWatched = false;
    private String namePrefix;

    public Integer getYearFrom() {
        int yearFrom = 1900;

        try {
            yearFrom = Integer.parseInt(this.yearFrom);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return yearFrom;
    }

    public Integer getYearTo() {
        int yearTo = 2100;

        try {
            yearTo = Integer.parseInt(this.yearTo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return yearTo;
    }

    public String getNamePrefix() {
        return namePrefix + "%";
    }
}
