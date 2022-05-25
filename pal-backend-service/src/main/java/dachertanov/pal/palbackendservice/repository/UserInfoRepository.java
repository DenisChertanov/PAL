package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackenddto.user.UserSearchOutDto;
import dachertanov.pal.palbackenddto.user.rating.UserRating;
import dachertanov.pal.palbackendservice.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    @Query("select new dachertanov.pal.palbackenddto.user.UserSearchOutDto(userInfo.userId, " +
            "userInfo.username, userInfo.firstName, userInfo.lastName, userInfo.imageUrl, " +
            "userStatistic.animeSpentHours, userStatistic.animeCount) " +
            "from UserInfo userInfo " +
            "inner join UserStatistic userStatistic " +
            "on userStatistic.userId = userInfo.userId " +
            "where concat(userInfo.firstName, ' ', userInfo.lastName) like :userPrefix")
    Page<UserSearchOutDto> findUsers(String userPrefix, Pageable pageable);

    Optional<UserInfo> findByUsernameEquals(String username);

    @Query("select new dachertanov.pal.palbackenddto.user.rating.UserRating(userInfo.userId, userInfo.imageUrl, " +
            "userInfo.firstName, userInfo.lastName, userInfo.username, count(userInfo.userId)) " +
            "from UserInfo userInfo " +
            "inner join UserAnimeActivity userAnimeActivity on userInfo.userId = userAnimeActivity.userId " +
            "where userAnimeActivity.dateTimeWatched is not null and " +
            "userAnimeActivity.dateTimeWatched >= :startPeriod and " +
            "userAnimeActivity.dateTimeWatched <= :endPeriod " +
            "group by userInfo " +
            "order by count(userInfo.userId) desc ")
    List<UserRating> getUserRating(LocalDateTime startPeriod, LocalDateTime endPeriod);
}
