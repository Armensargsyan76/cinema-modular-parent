package am.itspace.cinemamodularcommon.dto.filmrequestdto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorRequestDTO {

    @NotEmpty(message = "actor's name can't be empty")
    private String name;
    @NotEmpty(message = "actor's surname can't be empty")
    private String surname;
    private String country;
    @NotNull(message = "actor's date born can't be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBorn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDied;
    private String pictureUrl;
    private String biography;

}
