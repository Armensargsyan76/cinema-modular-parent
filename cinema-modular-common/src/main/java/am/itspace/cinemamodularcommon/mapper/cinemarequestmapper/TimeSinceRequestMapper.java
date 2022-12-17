package am.itspace.cinemamodularcommon.mapper.cinemarequestmapper;

import am.itspace.cinemamodularcommon.dto.cinemarequestdto.TimeSinceRequestDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeSinceRequestMapper {

    TimeSince map(TimeSinceRequestDTO timeSinceRequestDTO);

}
