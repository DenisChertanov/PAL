package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackenddto.user.UserSearchInDto;
import dachertanov.pal.palbackenddto.user.UserSearchOutDto;
import dachertanov.pal.palbackenddto.user.rating.UserRating;
import dachertanov.pal.palbackenddto.user.rating.UserRatingListOutDto;
import dachertanov.pal.palbackendservice.entity.AnimePlaylist;
import dachertanov.pal.palbackendservice.entity.UserInfo;
import dachertanov.pal.palbackendservice.mapper.UserInfoMapper;
import dachertanov.pal.palbackendservice.repository.UserInfoRepository;
import dachertanov.pal.palbackendservice.s3.S3Service;
import dachertanov.pal.palbackendservice.security.config.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;
    private final S3Service s3Service;

    public Optional<UserInfoOutDto> getUserById(UUID userId) {
        return userInfoRepository.findById(userId)
                .map(userInfoMapper::entityToOutDto);
    }

    @Transactional
    public Optional<UserInfoOutDto> getUserByUsername(String username) {
        return userInfoRepository.findByUsernameEquals(username)
                .map(userInfoMapper::entityToOutDto);
    }

    public Page<UserSearchOutDto> findUsers(UserSearchInDto userSearchInDto) {
        return userInfoRepository.findUsers(userSearchInDto.getUserPrefix() + "%",
                PageRequest.of(userSearchInDto.getPage().getPageNumber(), userSearchInDto.getPage().getPageSize()));
    }

    @Transactional
    public UserInfoOutDto uploadUserImage(MultipartFile file) throws Exception {
        UserInfo userInfo = getCurrentUserInfo();
        String imageUrl = s3Service.uploadImage(file, getCurrentUserInfo().getUserId().toString());
        userInfo.setImageUrl(imageUrl);
        userInfoRepository.save(userInfo);

        return userInfoMapper.entityToOutDto(userInfo);
    }

    @Transactional
    public UserInfoOutDto getCurrentUserInfoOutDto() {
        return userInfoMapper.entityToOutDto(getCurrentUserInfo());
    }

    @Transactional
    public UserRatingListOutDto getUserActivityRating() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime currentMonthFirstDay = LocalDateTime.of(
                currentDateTime.getYear(),
                currentDateTime.getMonth(),
                1,
                0,
                0,
                0
        );
        List<UserRating> allUserRating = userInfoRepository.getUserRating(currentMonthFirstDay, currentDateTime);

        UserRatingListOutDto result = new UserRatingListOutDto(
                allUserRating.subList(0, Math.min(20, allUserRating.size())),
                String.format("%02d.%d", currentDateTime.getMonthValue(), currentDateTime.getYear())
        );
        long order = 1;
        for (var userRating : result.getUserRatingList()) {
            userRating.setOrder(order);
            order++;
        }

        return result;
    }

    private UserInfo getCurrentUserInfo() {
        UUID userId = SecurityUtil.getCurrentUserId();
        return userInfoRepository.findById(userId)
                .orElseThrow(() -> {
                    String logMessage = "User with id = " + userId + " not found";
                    log.error(logMessage);
                    return new HttpClientErrorException(HttpStatus.BAD_REQUEST, logMessage);
                });
    }
}
