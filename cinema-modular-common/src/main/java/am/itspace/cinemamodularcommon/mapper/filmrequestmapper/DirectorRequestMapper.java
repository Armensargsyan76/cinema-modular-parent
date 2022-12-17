package am.itspace.cinemamodularcommon.mapper.filmrequestmapper;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.DirectorRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmrequestdto.DirectorUpdateRequestDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Director;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DirectorRequestMapper {

    Director map(DirectorRequestDTO directorRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Director map(DirectorUpdateRequestDTO directorUpdateRequestDTO);

}
