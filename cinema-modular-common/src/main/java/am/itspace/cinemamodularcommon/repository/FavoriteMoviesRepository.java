package am.itspace.cinemamodularcommon.repository;


import am.itspace.cinemamodularcommon.entity.userdetail.FavoriteMovies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteMoviesRepository extends JpaRepository<FavoriteMovies, Integer> {

    Optional<FavoriteMovies> findByFilm_idAndUser_id(int film_id, int user_id);

}
