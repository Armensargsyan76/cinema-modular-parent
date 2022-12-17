package am.itspace.cinemamodularcommon.mapper.filmrequestmapper;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.ActorRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmrequestdto.ActorUpdateRequestDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Actor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ActorRequestMapper {

    Actor map(ActorRequestDTO actorRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Actor map(ActorUpdateRequestDTO actorUpdateRequestDTO);

}
