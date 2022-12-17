package am.itspace.cinemamodularcommon.mapper.cinemarequestmapper;

import am.itspace.cinemamodularcommon.dto.cinemarequestdto.BoxOfficeRequestDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.BoxOffice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoxOfficeRequestMapper {

    BoxOffice map(BoxOfficeRequestDTO boxOfficeRequestDTO);

}
