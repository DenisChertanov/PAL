package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackenddto.user.UserSearchOutDto;
import dachertanov.pal.palbackendservice.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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
}
