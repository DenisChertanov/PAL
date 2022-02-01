package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.user.UserFriendListOutDto;
import dachertanov.pal.palbackenddto.user.UserFriendOutDto;
import dachertanov.pal.palbackendservice.entity.UserFriend;
import dachertanov.pal.palbackendservice.mapper.UserFriendMapper;
import dachertanov.pal.palbackendservice.repository.UserFriendRepository;
import dachertanov.pal.palbackendservice.repository.UserInfoRepository;
import dachertanov.pal.palbackendservice.security.config.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserFriendService {
    private final UserFriendMapper userFriendMapper;
    private final UserFriendRepository userFriendRepository;
    private final UserInfoRepository userInfoRepository;

    public Optional<UserFriendOutDto> addFriend(UUID friendId) {
        validateFriendId(friendId);
        UUID userId = SecurityUtil.getCurrentUserId();

        UserFriend userFriend = userFriendRepository.save(new UserFriend(userId, friendId));
        return Optional.of(userFriendMapper.entityToOutDto(userFriend));
    }

    public Optional<UserFriendListOutDto> getAllFriends() {
        UUID userId = SecurityUtil.getCurrentUserId();
        return Optional.of(userFriendMapper.listEntitiesToOutDto(userFriendRepository.findAllByUserIdEquals(userId), userId));
    }

    private void validateFriendId(UUID friendId) {
        if (!userInfoRepository.existsById(friendId)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Пользователь с id \"" + friendId + "\" не найден");
        }
    }
}
