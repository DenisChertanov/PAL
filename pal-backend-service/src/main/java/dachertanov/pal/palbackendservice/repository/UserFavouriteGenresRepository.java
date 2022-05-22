package dachertanov.pal.palbackendservice.repository;

import dachertanov.pal.palbackendservice.entity.UserFavouriteGenres;
import dachertanov.pal.palbackendservice.entity.id.UserFavouriteGenresId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserFavouriteGenresRepository extends JpaRepository<UserFavouriteGenres, UserFavouriteGenresId> {
    List<UserFavouriteGenres> findAllByUserIdEqualsOrderByRecommendationMarkDesc(UUID userId);
}
