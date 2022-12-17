package am.itspace.cinemamodularcommon.dto.userresponsedto;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.userdetail.Role;
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
public class UserResponseDTO {

    private int id;
    private String name;
    private String surname;
    private String email;
    private List<Film> films;
    private Role role;
    private String pictureUrl;
    private LocalDate registeredDate;

}
