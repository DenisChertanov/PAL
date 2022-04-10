package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.search.Type;
import dachertanov.pal.palbackendservice.entity.AnimeType;
import org.springframework.stereotype.Component;

@Component
public class AnimeTypeMapper {
    public Type dtoFromEntity(AnimeType animeType) {
        Type type = new Type();
        type.setId(animeType.getAnimeTypeId());
        type.setName(animeType.getType());

        return type;
    }
}
