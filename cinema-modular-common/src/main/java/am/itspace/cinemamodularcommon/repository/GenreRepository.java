package am.itspace.cinemamodularcommon.repository;


import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
