package am.itspace.cinemamodularcommon.dto.cinemaresponsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSinceResponseDTO {

    private int id;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;

}
