package am.itspace.cinemamodularcommon.mapper.cinemarequestmapper;

import am.itspace.cinemamodularcommon.dto.cinemarequestdto.CinemaRequestDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.Cinema;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CinemaRequestMapper {

    Cinema map(CinemaRequestDTO cinemaRequestDTO);

}
