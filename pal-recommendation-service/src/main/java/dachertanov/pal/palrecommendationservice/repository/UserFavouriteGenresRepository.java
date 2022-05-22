package dachertanov.pal.palrecommendationservice.repository;

import dachertanov.pal.palrecommendationservice.entity.UserFavouriteGenres;
import dachertanov.pal.palrecommendationservice.entity.id.UserFavouriteGenresId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavouriteGenresRepository extends JpaRepository<UserFavouriteGenres, UserFavouriteGenresId> {
}
