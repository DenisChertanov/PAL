package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.playlist.AnimePlaylistOutDto;
import dachertanov.pal.palbackendservice.entity.AnimePlaylist;
import dachertanov.pal.palbackendservice.entity.AnimePlaylistUserMark;
import dachertanov.pal.palbackendservice.entity.UserInfo;
import dachertanov.pal.palbackendservice.entity.id.AnimePlaylistMapperId;
import dachertanov.pal.palbackendservice.entity.id.AnimePlaylistUserMarkId;
import dachertanov.pal.palbackendservice.mapper.AnimePlaylistMapper;
import dachertanov.pal.palbackendservice.repository.AnimePlaylistMapperRepository;
import dachertanov.pal.palbackendservice.repository.AnimePlaylistRepository;
import dachertanov.pal.palbackendservice.repository.AnimePlaylistUserMarkRepository;
import dachertanov.pal.palbackendservice.repository.UserInfoRepository;
import dachertanov.pal.palbackendservice.s3.S3Service;
import dachertanov.pal.palbackendservice.security.config.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AnimePlaylistService {
    private final UserInfoRepository userInfoRepository;
    private final AnimePlaylistRepository animePlaylistRepository;
    private final AnimePlaylistMapper animePlaylistMapper;
    private final AnimePlaylistMapperRepository animePlaylistMapperRepository;
    private final AnimePlaylistUserMarkRepository animePlaylistUserMarkRepository;
    private final S3Service s3Service;

    @Transactional
    public AnimePlaylistOutDto createEmptyPlaylist() {
        UserInfo userInfo = getCurrentUserInfo();
        AnimePlaylist animePlaylist = new AnimePlaylist();
        animePlaylist.setUserInfo(userInfo);
        animePlaylist.setTimeCreated(LocalDateTime.now());
        animePlaylist = animePlaylistRepository.save(animePlaylist);

        return makeDtoByAnimePlaylistId(animePlaylist.getAnimePlaylistId());
    }

    @Transactional
    public AnimePlaylistOutDto uploadAnimePlaylistImage(MultipartFile file, UUID animePlaylistId) throws Exception {
        AnimePlaylist animePlaylist = getAnimePlaylistById(animePlaylistId);
        String imageUrl = s3Service.uploadImage(file, animePlaylistId.toString());
        animePlaylist.setImageUrl(imageUrl);
        animePlaylistRepository.save(animePlaylist);

        return makeDtoByAnimePlaylistId(animePlaylistId);
    }

    @Transactional
    public AnimePlaylistOutDto changeTitle(String title, UUID animePlaylistId) {
        AnimePlaylist animePlaylist = getAnimePlaylistById(animePlaylistId);
        animePlaylist.setTitle(title);
        animePlaylistRepository.save(animePlaylist);

        return makeDtoByAnimePlaylistId(animePlaylistId);
    }

    @Transactional
    public AnimePlaylistOutDto changeDescription(String description, UUID animePlaylistId) {
        AnimePlaylist animePlaylist = getAnimePlaylistById(animePlaylistId);
        animePlaylist.setDescription(description);
        animePlaylistRepository.save(animePlaylist);

        return makeDtoByAnimePlaylistId(animePlaylistId);
    }

    @Transactional
    public AnimePlaylistOutDto setMark(Integer mark, UUID animePlaylistId) {
        AnimePlaylist animePlaylist = getAnimePlaylistById(animePlaylistId);
        UUID userId = getCurrentUserInfo().getUserId();

        AnimePlaylistUserMark animePlaylistUserMark = animePlaylistUserMarkRepository
                .findById(new AnimePlaylistUserMarkId(animePlaylistId, userId))
                .orElse(new AnimePlaylistUserMark(animePlaylistId, userId, mark));
        animePlaylistUserMark.setMark(mark);
        animePlaylistUserMarkRepository.save(animePlaylistUserMark);

        animePlaylist.setMark(findNewPlaylistGeneralMark(animePlaylistId));
        animePlaylistRepository.save(animePlaylist);

        return makeDtoByAnimePlaylistId(animePlaylistId);
    }

    @Transactional
    public AnimePlaylistOutDto getById(UUID animePlaylistId) {
        return makeDtoByAnimePlaylistId(animePlaylistId);
    }

    @Transactional
    public AnimePlaylistOutDto pushBackAnime(UUID animeId, UUID animePlaylistId) {
        if (animePlaylistMapperRepository
                .findById(new AnimePlaylistMapperId(animePlaylistId, animeId))
                .isPresent()) {
            String logMessage = "Anime with id = " + animeId + " is already exists in animePlaylist with id = " + animePlaylistId;
            log.error(logMessage);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, logMessage);
        }

        Integer maxSequence = animePlaylistMapperRepository.findMaxSequence(animePlaylistId);
        dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper animePlaylistMapper =
                new dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper(
                        animePlaylistId,
                        animeId,
                        maxSequence == null
                                ? 1
                                : maxSequence + 1
                );
        animePlaylistMapperRepository.save(animePlaylistMapper);

        return makeDtoByAnimePlaylistId(animePlaylistId);
    }

    @Transactional
    public AnimePlaylistOutDto changeAnimeOrder(UUID animeId, UUID animePlaylistId, Integer order) {
        dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper animePlaylistMapper =
                getAnimePlaylistMapperById(animeId, animePlaylistId);
        Integer oldSequence = animePlaylistMapper.getSequence();
        Integer maxSequence = animePlaylistMapperRepository.findMaxSequence(animePlaylistId);

        if (order > maxSequence) {
            String logMessage = "Given order greater then max sequence";
            log.error(logMessage);
            throw new RuntimeException(logMessage);
        }

        int appender = order > oldSequence
                ? -1
                : 1;
        List<dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper> mapperList =
                animePlaylistMapperRepository.findAllByAnimePlaylistIdEquals(animePlaylistId);
        for (var mapper : mapperList) {
            int mapperSequence = mapper.getSequence();
            if (mapperSequence >= Math.min(order, oldSequence)
                    && mapperSequence <= Math.max(order, oldSequence)) {
                mapper.setSequence(mapperSequence + appender);
                animePlaylistMapperRepository.save(mapper);
            }
        }
        animePlaylistMapper.setSequence(order);
        animePlaylistMapperRepository.save(animePlaylistMapper);

        return makeDtoByAnimePlaylistId(animePlaylistId);
    }

    @Transactional
    public AnimePlaylistOutDto removeAnime(UUID animeId, UUID animePlaylistId) {
        dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper animePlaylistMapper =
                getAnimePlaylistMapperById(animeId, animePlaylistId);
        Integer oldSequence = animePlaylistMapper.getSequence();

        List<dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper> mapperList =
                animePlaylistMapperRepository.findAllByAnimePlaylistIdEquals(animePlaylistId);
        for (var mapper : mapperList) {
            int mapperSequence = mapper.getSequence();
            if (mapperSequence > oldSequence) {
                mapper.setSequence(mapperSequence - 1);
                animePlaylistMapperRepository.save(mapper);
            }
        }

        animePlaylistMapperRepository.delete(animePlaylistMapper);

        return makeDtoByAnimePlaylistId(animePlaylistId);
    }

    @Transactional
    public List<AnimePlaylistOutDto> getAllUserPlaylists(UUID userId) {
        List<AnimePlaylist> userPlaylists = animePlaylistRepository.findAllByUserId(userId);

        return userPlaylists.stream()
                .map(animePlaylist -> makeDtoByAnimePlaylistId(animePlaylist.getAnimePlaylistId()))
                .collect(Collectors.toList());
    }

    public List<AnimePlaylistOutDto> getAllCurrentUserPlaylists() {
        return getAllUserPlaylists(getCurrentUserInfo().getUserId());
    }

    private AnimePlaylistOutDto makeDtoByAnimePlaylistId(UUID animePlaylistId) {
        AnimePlaylist animePlaylist = getAnimePlaylistById(animePlaylistId);
        List<dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper> mapperList = getMapperListByAnimePlaylistId(animePlaylistId);
        Integer userMark = getCurrentUserMarkForPlaylist(animePlaylistId);

        return animePlaylistMapper.makeOutDto(animePlaylist, userMark, mapperList);
    }

    private AnimePlaylist getAnimePlaylistById(UUID animePlaylistId) {
        return animePlaylistRepository.findById(animePlaylistId)
                .orElseThrow(() -> {
                    String logMessage = "AnimePlaylist with id = " + animePlaylistId + " doesn't exists";
                    log.error(logMessage);
                    return new RuntimeException(logMessage);
                });
    }

    private dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper getAnimePlaylistMapperById(UUID animeId, UUID animePlaylistId) {
        return animePlaylistMapperRepository.findById(new AnimePlaylistMapperId(animePlaylistId, animeId))
                .orElseThrow(() -> {
                    String logMessage = "AnimePlaylistMapper with animePlaylistId = " + animePlaylistId +
                            " and animeId = " + animeId + " doesn't exists";
                    log.error(logMessage);
                    return new HttpClientErrorException(HttpStatus.BAD_REQUEST, logMessage);
                });
    }

    private List<dachertanov.pal.palbackendservice.entity.AnimePlaylistMapper> getMapperListByAnimePlaylistId(UUID animePlaylistId) {
        return animePlaylistMapperRepository.findAllByAnimePlaylistIdEquals(animePlaylistId);
    }

    private Integer getCurrentUserMarkForPlaylist(UUID animePlaylistId) {
        return animePlaylistUserMarkRepository.findById(new AnimePlaylistUserMarkId(animePlaylistId, getCurrentUserInfo().getUserId()))
                .map(AnimePlaylistUserMark::getMark)
                .orElse(0);
    }

    private Integer findNewPlaylistGeneralMark(UUID animePlaylistId) {
        Double generalMark = animePlaylistUserMarkRepository.findGeneralMark(animePlaylistId);
        return generalMark == null
                ? 0
                : (int) Math.round(generalMark);
    }

    private UserInfo getCurrentUserInfo() {
        Optional<UserInfo> opUserInfo = userInfoRepository.findById(SecurityUtil.getCurrentUserId());

        return opUserInfo.orElseThrow(() -> {
            String logMessage = "User with userId = " + SecurityUtil.getCurrentUserId() + " doesn't exists";
            log.error(logMessage);
            return new RuntimeException(logMessage);
        });
    }
}
