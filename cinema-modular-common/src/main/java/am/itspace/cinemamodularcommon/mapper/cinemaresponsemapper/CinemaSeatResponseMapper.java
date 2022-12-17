package am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper;

import am.itspace.cinemamodularcommon.dto.cinemaresponsedto.CinemaSeatResponseDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.CinemaSeat;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CinemaSeatResponseMapper {

    CinemaSeatResponseDTO map(CinemaSeat cinemaSeat);

    List<CinemaSeatResponseDTO> map(List<CinemaSeat> cinemaSeats);

}
