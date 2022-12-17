package am.itspace.cinemamodularcommon.dto.cinemaresponsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaResponseDTO {

    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private int spaciousness;
    private String pictureUrl;
    private double price;

}
