package am.itspace.cinemamodularrest.dto;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestErrorDTO {

    private int statusCode;
    private String errorMessage;

}
