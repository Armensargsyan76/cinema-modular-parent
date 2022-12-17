package am.itspace.cinemamodularcommon.dto.cinemarequestdto;

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
public class TimeSinceRequestDTO {

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;

}
