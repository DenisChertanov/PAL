package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.search.out.State;
import dachertanov.pal.palbackendservice.entity.AnimeState;
import org.springframework.stereotype.Component;

@Component
public class AnimeStateMapper {
    public State dtoFromEntity(AnimeState animeState) {
        State state = new State();
        state.setId(animeState.getAnimeStateId());
        state.setName(animeState.getState());

        return state;
    }
}
