package am.itspace.cinemamodularcommon.dto.filmrequestdto;

import am.itspace.cinemamodularcommon.entity.filmdetail.Status;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmRequestDTO {

    @NotEmpty(message = "film's title can't be empty")
    private String originalTitle;
    @NotNull(message = "film's premiere date can't be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate premiere;
    @NotNull(message = "film's durationMinute can't be empty or invalid minute")
    private int durationMinute;
    private String description;
    private String country;
    @NotEmpty(message = "film's videoUrl can't be empty")
    private String videoUrl;
    private String pictureUrl;
    @NotNull(message = "film's director can't be empty")
    private int directorId;
    @NotNull(message = "film's actors can't be empty")
    private List<Integer> actorsId;
    @NotNull(message = "film's genres can't be empty")
    private List<Integer> genresId;
    @NotEmpty(message = "film's AgeLimit can't be empty")
    private String ageLimit;
    @NotNull(message = "film's' Status can't be empty")
    private Status status;
    private List<Integer> timeSinceId;

}
