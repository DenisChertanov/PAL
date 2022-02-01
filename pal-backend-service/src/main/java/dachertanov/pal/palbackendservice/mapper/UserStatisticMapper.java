package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.user.UserStatisticOutDto;
import dachertanov.pal.palbackendservice.entity.UserStatistic;
import org.springframework.stereotype.Component;

@Component
public class UserStatisticMapper {
    public UserStatisticOutDto entityToOutDto(UserStatistic userStatistic) {
        UserStatisticOutDto dto = new UserStatisticOutDto();
        dto.setUserId(userStatistic.getUserId());
        dto.setAnimeSpentHours(userStatistic.getAnimeSpentHours());
        dto.setAnimeCount(userStatistic.getAnimeCount());

        return dto;
    }
}
