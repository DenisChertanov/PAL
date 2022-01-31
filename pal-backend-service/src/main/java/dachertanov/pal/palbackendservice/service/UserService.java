package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackendservice.mapper.UserInfoMapper;
import dachertanov.pal.palbackendservice.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

    public Optional<UserInfoOutDto> getUserById(UUID userId) {
        return userInfoRepository.findById(userId)
                .map(userInfoMapper::entityToOutDto);
    }
}
