package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackenddto.user.UserSearchInDto;
import dachertanov.pal.palbackenddto.user.UserSearchOutDto;
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
