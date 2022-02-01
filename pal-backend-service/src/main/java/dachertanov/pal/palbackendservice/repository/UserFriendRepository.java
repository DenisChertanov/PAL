package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.UserFriend;
import dachertanov.pal.palbackendservice.entity.id.UserFriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserFriendRepository extends JpaRepository<UserFriend, UserFriendId> {
    List<UserFriend> findAllByUserIdEquals(UUID userId);
}
