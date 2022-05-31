package dachertanov.pal.palbackendservice.service;

import dachertanov.pal.palbackenddto.anime.UserAnimeActivityType;
import dachertanov.pal.palbackendservice.entity.Anime;
import dachertanov.pal.palbackendservice.entity.AnimePlaylist;
import dachertanov.pal.palbackendservice.entity.UserInfo;
import dachertanov.pal.palbackendservice.repository.AnimePlaylistRepository;
import dachertanov.pal.palbackendservice.repository.AnimeRepository;
import dachertanov.pal.palbackendservice.repository.UserInfoRepository;
import dachertanov.pal.palbackendservice.security.config.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestService {
    private final UserInfoRepository userInfoRepository;
    private final AnimeRepository animeRepository;
    private final UserAnimeActivityService userAnimeActivityService;
    private final AnimePlaylistRepository animePlaylistRepository;
    private final AnimePlaylistService animePlaylistService;

    /**
     * Для всех пользователей все аниме делает просмотренными
     */
    @Transactional
    public void initializeAllUsers() {
        List<UserInfo> allUsers = userInfoRepository.findAll();
        List<Anime> allAnime = animeRepository.findAll();

        for (var user : allUsers) {
            SecurityUtil.rootMode = true;
            SecurityUtil.rootId = user.getUserId();

            for (var anime : allAnime) {
                userAnimeActivityService.updateActivity(anime.getAnimeId(),
                        anime.getEpisodes(), UserAnimeActivityType.LAST_WATCHED_EPISODE);
            }
        }
        SecurityUtil.rootMode = false;
    }

    @Transactional
    public void createUserTestPlaylist(UUID userId, MultipartFile image) throws Exception {
        SecurityUtil.rootMode = true;
        SecurityUtil.rootId = userId;

        var dto = animePlaylistService.createEmptyPlaylist();

        AnimePlaylist animePlaylist = animePlaylistRepository.getById(dto.getAnimePlaylistId());
        animePlaylist.setTitle("Посмотреть в будущем");
        animePlaylist.setDescription("Сборник планируемых к просмотру аниме, когда-нибудь до них дойдёт...");
        animePlaylistRepository.save(animePlaylist);

        animePlaylistService.uploadAnimePlaylistImage(image, animePlaylist.getAnimePlaylistId());

        List<Anime> randomAnime = get5RandomAnime();
        for (var anime : randomAnime) {
            animePlaylistService.pushBackAnime(anime.getAnimeId(), animePlaylist.getAnimePlaylistId());
        }

        SecurityUtil.rootMode = false;
    }

    private List<Anime> get5RandomAnime() {
        List<Anime> allAnime = animeRepository.findAll();
        Collections.shuffle(allAnime);

        return allAnime.subList(0, 5);
    }
}
