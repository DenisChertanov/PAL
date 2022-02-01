package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.user.UserStatisticOutDto;
import dachertanov.pal.palbackendservice.mapper.UserStatisticMapper;
import dachertanov.pal.palbackendservice.repository.UserStatisticRepository;
import dachertanov.pal.palbackendservice.security.config.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserStatisticService {
    private final UserStatisticMapper userStatisticMapper;
    private final UserStatisticRepository userStatisticRepository;

    public Optional<UserStatisticOutDto> getUserStatistic() {
        return userStatisticRepository.findById(SecurityUtil.getCurrentUserId())
                .map(userStatisticMapper::entityToOutDto);
    }
}
