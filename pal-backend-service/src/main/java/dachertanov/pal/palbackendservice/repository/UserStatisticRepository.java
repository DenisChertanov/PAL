package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.UserStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserStatisticRepository extends JpaRepository<UserStatistic, UUID> {
}
