package am.itspace.cinemamodularcommon.repository;


import am.itspace.cinemamodularcommon.entity.filmdetail.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer> {

}
