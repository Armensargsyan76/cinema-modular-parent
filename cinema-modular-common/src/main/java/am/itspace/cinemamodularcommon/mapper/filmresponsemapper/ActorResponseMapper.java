package am.itspace.cinemamodularcommon.mapper.filmresponsemapper;

import am.itspace.cinemamodularcommon.dto.filmresponsedto.ActorResponseDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Actor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorResponseMapper {

    ActorResponseDTO map(Actor actor);

    List<ActorResponseDTO> map(List<Actor> actors);

}
