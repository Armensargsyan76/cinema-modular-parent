package am.itspace.cinemamodularcommon.mapper.filmresponsemapper;

import am.itspace.cinemamodularcommon.dto.filmresponsedto.GenreResponseDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreResponseMapper {

    GenreResponseDTO map(Genre genre);

    List<GenreResponseDTO> map(List<Genre> genres);

}
