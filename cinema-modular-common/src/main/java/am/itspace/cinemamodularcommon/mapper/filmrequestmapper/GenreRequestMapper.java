package am.itspace.cinemamodularcommon.mapper.filmrequestmapper;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.GenreRequestDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreRequestMapper {

    Genre map(GenreRequestDTO genreRequestDTO);

}
