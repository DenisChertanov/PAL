package dachertanov.pal.palbackendservice.security;

import dachertanov.pal.palbackenddto.user.UserInfoInDto;
import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackendservice.entity.UserInfo;
import dachertanov.pal.palbackendservice.entity.UserStatistic;
import dachertanov.pal.palbackendservice.mapper.UserInfoMapper;
import dachertanov.pal.palbackendservice.repository.UserInfoRepository;
import dachertanov.pal.palbackendservice.repository.UserStatisticRepository;
import dachertanov.pal.palbackendservice.security.config.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private final UserInfoRepository userInfoRepository;
    private final Pbkdf2PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserInfoMapper userInfoMapper;
    private final UserStatisticRepository userStatisticRepository;

    /**
     * Возвращает jwt токен по переданным Basic Auth
     */
    public String signIn(String authHeader) {
        String[] credentials = getAuthCredentials(authHeader);
        String userId = credentials[0];
        String userPassword = credentials[1];

        Optional<UserInfo> userOptional = userInfoRepository.findById(UUID.fromString(userId));
        if (userOptional.isPresent()) {
            UserInfo userInfo = userOptional.get();
            if (passwordEncoder.matches(userPassword, userInfo.getHashPassword())) {
                return jwtUtils.generateToken(userId);
            } else {
                log.error("Пользователь с user_id \"" + userId + "\" имеет другой пароль");
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Пользователь с user_id \"" + userId + "\" имеет другой пароль");
            }
        } else {
            log.error("Пользователь с user_id \"" + userId + "\" не найден");
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Пользователь с user_id \"" + userId + "\" не найден");
        }
    }

    /**
     * Возвращает DTO с зарегистрированным пользователем
     */
    public UserInfoOutDto signUp(UserInfoInDto userInfoInDto) {
        UserInfo userInfo = userInfoMapper.inDtoToEntity(userInfoInDto);
        userInfo = userInfoRepository.save(userInfo);
        userStatisticRepository.save(new UserStatistic(userInfo.getUserId()));

        return userInfoMapper.entityToOutDto(userInfo);
    }

    /**
     * Парсит Basic Auth на username и password
     */
    private String[] getAuthCredentials(String authHeader) {
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":", 2);
    }
}
