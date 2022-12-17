package am.itspace.cinemamodularcommon.mapper.filmresponsemapper;

import am.itspace.cinemamodularcommon.dto.filmresponsedto.FilmResponseDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilmResponseMapper {

    FilmResponseDTO map(Film film);

    List<FilmResponseDTO> map(List<Film> films);
}
