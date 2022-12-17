package am.itspace.cinemamodularcommon.mapper.filmrequestmapper;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.FilmRequestDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmRequestMapper {

    Film map(FilmRequestDTO filmRequestDTO);

}
