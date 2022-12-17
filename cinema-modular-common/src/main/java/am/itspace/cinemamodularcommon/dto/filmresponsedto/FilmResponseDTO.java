package am.itspace.cinemamodularcommon.dto.filmresponsedto;

import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import am.itspace.cinemamodularcommon.entity.filmdetail.Actor;
import am.itspace.cinemamodularcommon.entity.filmdetail.Director;
import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmResponseDTO {

    private int id;
    private String originalTitle;
    private LocalDate premiere;
    private int durationMinute;
    private String description;
    private String country;
    private Double rating;
    private String videoUrl;
    private String pictureUrl;
    private Director director;
    private List<Actor> actors;
    private List<Genre> genres;
    private String ageLimit;
    private List<TimeSince> times;

}
