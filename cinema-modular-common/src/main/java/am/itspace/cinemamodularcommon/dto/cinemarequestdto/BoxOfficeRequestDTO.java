package am.itspace.cinemamodularcommon.dto.cinemarequestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoxOfficeRequestDTO {

    @NotNull(message = "time since can't be empty")
    private int timeId;
    private double price;
    private int userId;
    private int cinemaId;
    private int filmId;
    @NotNull(message = "seating number can't be empty")
    private int seatingNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
