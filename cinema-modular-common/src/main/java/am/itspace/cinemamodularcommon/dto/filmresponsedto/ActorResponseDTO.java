package am.itspace.cinemamodularcommon.dto.filmresponsedto;

import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorResponseDTO {

    private int id;
    private String name;
    private String surname;
    private String country;
    private LocalDate dateBorn;
    private LocalDate dateDied;
    private int age;
    private String pictureUrl;
    private String biography;

}
