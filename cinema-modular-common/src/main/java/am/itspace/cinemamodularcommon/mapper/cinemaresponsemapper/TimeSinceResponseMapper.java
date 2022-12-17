package am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper;

import am.itspace.cinemamodularcommon.dto.cinemaresponsedto.TimeSinceResponseDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeSinceResponseMapper {

    TimeSinceResponseDTO map(TimeSince timeSince);
    List<TimeSinceResponseDTO> map(List<TimeSince> timeSince);

}
