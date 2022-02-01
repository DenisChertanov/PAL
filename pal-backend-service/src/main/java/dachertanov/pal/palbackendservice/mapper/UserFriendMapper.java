package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.user.UserFriendListOutDto;
import dachertanov.pal.palbackenddto.user.UserFriendOutDto;
import dachertanov.pal.palbackendservice.entity.UserFriend;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserFriendMapper {
    public UserFriendListOutDto listEntitiesToOutDto(List<UserFriend> entityList, UUID userId) {
        UserFriendListOutDto dto = new UserFriendListOutDto();
        dto.setUserId(userId);
        dto.setFriendList(entityList.stream().map(UserFriend::getFriendId).collect(Collectors.toList()));

        return dto;
    }

    public UserFriendOutDto entityToOutDto(UserFriend userFriend) {
        UserFriendOutDto dto = new UserFriendOutDto();
        dto.setUserId(userFriend.getUserId());
        dto.setFriendId(userFriend.getFriendId());

        return dto;
    }
}
