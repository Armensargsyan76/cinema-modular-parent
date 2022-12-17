package am.itspace.cinemamodularcommon.repository;


import am.itspace.cinemamodularcommon.entity.userdetail.UserFilm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFilmRepository extends JpaRepository<UserFilm, Integer> {
    Optional<UserFilm> findAllByFilm_idAndUser_id(int film_id, int user_id);

    void deleteById(int id);


}
