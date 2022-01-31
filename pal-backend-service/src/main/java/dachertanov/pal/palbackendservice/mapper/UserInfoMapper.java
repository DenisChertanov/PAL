package dachertanov.pal.palbackendservice.mapper;

import dachertanov.pal.palbackenddto.user.UserInfoInDto;
import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackendservice.entity.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserInfoMapper {
    private Pbkdf2PasswordEncoder passwordEncoder;

    public UserInfo inDtoToEntity(UserInfoInDto dto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(dto.getUsername());
        userInfo.setHashPassword(passwordEncoder.encode(dto.getPassword()));
        userInfo.setFirstName(dto.getFirstName());
        userInfo.setLastName(dto.getLastName());
        userInfo.setEmail(dto.getEmail());
        userInfo.setPhoneNumber(dto.getPhoneNumber());

        return userInfo;
    }

    public UserInfoOutDto entityToOutDto(UserInfo userInfo) {
        UserInfoOutDto dto = new UserInfoOutDto();
        dto.setUserId(userInfo.getUserId());
        dto.setHashPassword(userInfo.getHashPassword());
        dto.setUserName(userInfo.getUsername());
        dto.setFirstName(userInfo.getFirstName());
        dto.setLastName(userInfo.getLastName());
        dto.setEmail(userInfo.getEmail());
        dto.setPhoneNumber(userInfo.getPhoneNumber());
        dto.setImageUrl(userInfo.getImageUrl());

        return dto;
    }
}
