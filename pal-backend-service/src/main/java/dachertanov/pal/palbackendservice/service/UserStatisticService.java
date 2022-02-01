package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.user.UserStatisticOutDto;
import dachertanov.pal.palbackendservice.mapper.UserStatisticMapper;
import dachertanov.pal.palbackendservice.repository.UserStatisticRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserStatisticService {
    private final UserStatisticMapper userStatisticMapper;
    private final UserStatisticRepository userStatisticRepository;

    public Optional<UserStatisticOutDto> getUserStatistic(UUID userId) {
        return userStatisticRepository.findById(userId)
                .map(userStatisticMapper::entityToOutDto);
    }
}
