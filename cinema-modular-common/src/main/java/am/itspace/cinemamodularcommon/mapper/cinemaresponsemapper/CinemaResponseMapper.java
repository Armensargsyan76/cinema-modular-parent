package am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper;

import am.itspace.cinemamodularcommon.dto.cinemaresponsedto.CinemaResponseDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.Cinema;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CinemaResponseMapper {

    CinemaResponseDTO map(Cinema cinema);
    List<CinemaResponseDTO> map(List<Cinema> cinemas);

}
