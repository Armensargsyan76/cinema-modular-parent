package am.itspace.cinemamodularcommon.dto.filmresponsedto;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectorResponseDTO {

    private int id;
    private String name;
    private String surname;
    private String country;
    private LocalDate dateBorn;
    private LocalDate dateDied;
    private int age;
    private String pictureUrl;
    private String biography;
    private List<Film> films;

}
