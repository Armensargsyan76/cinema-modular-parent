package am.itspace.cinemamodularcommon.repository;

import am.itspace.cinemamodularcommon.entity.cinemadetail.BoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface BoxOfficeRepository extends JpaRepository<BoxOffice, Integer> {

    @Query(value = "select * from box_office where date = :dateOrder and time_since = :timeSince and cinema_id = :cinemaId and film_id = :filmId", nativeQuery = true)
    Optional<BoxOffice> getBoxOfficeUnique(LocalDate dateOrder, LocalTime timeSince, int cinemaId, int filmId);

}
