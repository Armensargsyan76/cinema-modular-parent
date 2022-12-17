package am.itspace.cinemamodularcommon.dto.userrequestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDTO {

    private String name;
    private String surname;
    private String email;
    private String password;

}
