package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackenddto.user.UserSearchInDto;
import dachertanov.pal.palbackenddto.user.UserSearchOutDto;
import dachertanov.pal.palbackendservice.mapper.UserInfoMapper;
import dachertanov.pal.palbackendservice.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<UserSearchOutDto> findUsers(UserSearchInDto userSearchInDto) {
        return userInfoRepository.findUsers(userSearchInDto.getUserPrefix() + "%",
                PageRequest.of(userSearchInDto.getPage().getPageNumber(), userSearchInDto.getPage().getPageSize()));
    }
}
