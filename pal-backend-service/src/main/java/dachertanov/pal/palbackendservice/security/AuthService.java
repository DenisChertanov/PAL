package dachertanov.pal.palbackendservice.security;

import dachertanov.pal.palbackenddto.user.UserInfoInDto;
import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackendservice.entity.UserInfo;
import dachertanov.pal.palbackendservice.entity.UserStatistic;
import dachertanov.pal.palbackendservice.mapper.UserInfoMapper;
import dachertanov.pal.palbackendservice.repository.UserInfoRepository;
import dachertanov.pal.palbackendservice.repository.UserStatisticRepository;
import dachertanov.pal.palbackendservice.security.config.JwtUtils;
import dachertanov.pal.palbackendservice.service.UserStatisticService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class AuthService {
    private final UserInfoRepository userInfoRepository;
    private final Pbkdf2PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserInfoMapper userInfoMapper;
    private final UserStatisticRepository userStatisticRepository;
    private final UserStatisticService userStatisticService;
    private final UUID adminId;

    public AuthService(UserInfoRepository userInfoRepository,
                       Pbkdf2PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       UserInfoMapper userInfoMapper,
                       UserStatisticRepository userStatisticRepository,
                       UserStatisticService userStatisticService,
                       @Value("${pal.security.admin-id}") UUID adminId) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.userInfoMapper = userInfoMapper;
        this.userStatisticRepository = userStatisticRepository;
        this.userStatisticService = userStatisticService;
        this.adminId = adminId;
    }

    /**
     * Возвращает jwt токен по переданным Basic Auth
     */
    public String signIn(String authHeader) {
        String[] credentials = getAuthCredentials(authHeader);
        String username = credentials[0];
        String userPassword = credentials[1];

        Optional<UserInfo> userOptional = userInfoRepository.findByUsernameEquals(username);
        if (userOptional.isPresent()) {
            UserInfo userInfo = userOptional.get();
            if (passwordEncoder.matches(userPassword, userInfo.getHashPassword())) {
                return jwtUtils.generateToken(userInfo.getUserId().toString());
            } else {
                log.error("Пользователь с username \"" + username + "\" имеет другой пароль");
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Пользователь с username \"" + username + "\" имеет другой пароль");
            }
        } else {
            log.error("Пользователь с username \"" + username + "\" не найден");
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Пользователь с username \"" + username + "\" не найден");
        }
    }

    /**
     * Возвращает DTO с зарегистрированным пользователем
     */
    public UserInfoOutDto signUp(UserInfoInDto userInfoInDto) {
        validateUniqueUsername(userInfoInDto.getUsername());

        UserInfo userInfo = userInfoMapper.inDtoToEntity(userInfoInDto);
        userInfo.setUserId(UUID.randomUUID());
        userInfo = userInfoRepository.save(userInfo);
        userStatisticRepository.save(new UserStatistic(userInfo.getUserId()));

        userStatisticService.initFavouriteGenres(userInfo.getUserId());
        userStatisticService.initAnimeRecommendation(userInfo.getUserId());

        return userInfoMapper.entityToOutDto(userInfo);
    }

    public UserInfoOutDto signUpAdmin(UserInfoInDto userInfoInDto) {
        validateUniqueUsername(userInfoInDto.getUsername());

        UserInfo userInfo = userInfoMapper.inDtoToEntity(userInfoInDto);
        userInfo.setUserId(adminId);
        userInfo = userInfoRepository.save(userInfo);
        userStatisticRepository.save(new UserStatistic(userInfo.getUserId()));

        userStatisticService.initFavouriteGenres(userInfo.getUserId());
        userStatisticService.initAnimeRecommendation(userInfo.getUserId());

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

    private void validateUniqueUsername(String username) {
        userInfoRepository.findByUsernameEquals(username)
                .ifPresent(userInfo -> {
                    String logMessage = "User with username = " + username + " already exists";
                    log.error(logMessage);
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, logMessage);
                });
    }
}
