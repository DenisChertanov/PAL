package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
}
