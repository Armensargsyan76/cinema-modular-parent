package am.itspace.cinemamodularcommon.dto.cinemarequestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaRequestDTO {

    @NotEmpty(message = "cinema's Name can't be empty")
    private String name;
    @NotEmpty(message = "cinema's Address can't be empty")
    private String address;
    @NotEmpty(message = "cinema's PhoneNumber can't be empty")
    private String phoneNumber;
    @Email(message = "wrong Email form")
    @NotEmpty(message = "cinema's Email can't be empty")
    private String email;
    @NotNull(message = "cinema's Spaciousness can't be empty")
    private int spaciousness;
    private String pictureUrl;
    @NotNull(message = "cinema's Price can't be empty")
    private double price;

}
