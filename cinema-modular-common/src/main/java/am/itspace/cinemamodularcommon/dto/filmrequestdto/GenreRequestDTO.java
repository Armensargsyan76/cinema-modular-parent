package am.itspace.cinemamodularcommon.dto.filmrequestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreRequestDTO {

    @NotEmpty(message = "genre's name can't be empty")
    private String name;

}
