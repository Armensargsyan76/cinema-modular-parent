package am.itspace.cinemamodularcommon.mapper.filmresponsemapper;

import am.itspace.cinemamodularcommon.dto.filmresponsedto.DirectorResponseDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Director;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectorResponseMapper {

    DirectorResponseDTO map(Director director);

    List<DirectorResponseDTO> map(List<Director> directors);

}
