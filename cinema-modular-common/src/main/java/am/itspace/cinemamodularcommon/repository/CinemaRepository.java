package am.itspace.cinemamodularcommon.repository;


import am.itspace.cinemamodularcommon.entity.cinemadetail.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {

}
