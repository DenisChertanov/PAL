package dachertanov.pal.palbackendservice.security.aop;

import dachertanov.pal.palbackendservice.entity.AnimePlaylist;
import dachertanov.pal.palbackendservice.repository.AnimePlaylistRepository;
import dachertanov.pal.palbackendservice.security.config.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

/**
 * Аспект для валидации наличия прав при редактировании плейлистов
 */
@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class AnimePlaylistAspect {
    private final AnimePlaylistRepository animePlaylistRepository;

    @Transactional
    @Before("@annotation(ValidateUserHasPrivilegeForModifyPlaylist) && args(paramMap)")
    public void validateUserHasPrivilegeForModifyPlaylist(MultiValueMap<String, String> paramMap) {
        UUID animePlaylistId = UUID.fromString(paramMap.getFirst("animePlaylistId"));
        validateByPlaylistId(animePlaylistId);
    }

    @Transactional
    @Before("@annotation(ValidateUserHasPrivilegeForUploadPlaylistImage) && args(animePlaylistId,..)")
    public void validateUserHasPrivilegeForModifyPlaylist(String animePlaylistId) {
        validateByPlaylistId(UUID.fromString(animePlaylistId));
    }

    private void validateByPlaylistId(UUID animePlaylistId) {
        AnimePlaylist animePlaylist = getAnimePlaylistById(animePlaylistId);
        UUID authorId = animePlaylist.getUserInfo().getUserId();

        if (!SecurityUtil.getCurrentUserId().equals(authorId)) {
            String logMessage = "User with id = " + SecurityUtil.getCurrentUserId() +
                    " doesn't has privileges for modify animePlaylist with id = " + animePlaylistId;
            log.error(logMessage);
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, logMessage);
        }
    }

    private AnimePlaylist getAnimePlaylistById(UUID animePlaylistId) {
        return animePlaylistRepository.findById(animePlaylistId)
                .orElseThrow(() -> {
                    String logMessage = "AnimePlaylist with id = " + animePlaylistId + " doesn't exists";
                    log.error(logMessage);
                    return new HttpClientErrorException(HttpStatus.BAD_REQUEST, logMessage);
                });
    }
}
