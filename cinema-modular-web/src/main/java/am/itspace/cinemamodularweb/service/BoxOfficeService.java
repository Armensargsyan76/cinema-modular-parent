package am.itspace.cinemamodularweb.service;

import am.itspace.cinemamodularcommon.dto.cinemarequestdto.BoxOfficeRequestDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.BoxOffice;
import am.itspace.cinemamodularcommon.mapper.cinemarequestmapper.BoxOfficeRequestMapper;
import am.itspace.cinemamodularcommon.repository.BoxOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoxOfficeService {

    private final BoxOfficeRepository boxOfficeRepository;
    private final BoxOfficeRequestMapper boxOfficeRequestMapper;
    private final FilmService filmService;
    private final CinemaService cinemaService;
    private final UserService userService;
    private final TimeSinceService timeSinceService;

    public boolean createBoxOffice(BoxOfficeRequestDTO boxOfficeRequestDTO,
                                   int filmId, int cinemaId, int userId) {
        BoxOffice boxOffice = boxOfficeRequestMapper.map(boxOfficeRequestDTO);
        boxOffice.setTimeSince(timeSinceService.getById(boxOfficeRequestDTO.getTimeId()).getTime());
        boxOffice.setFilm(filmService.getFilmById(filmId));
        boxOffice.setCinema(cinemaService.getCinemaById(cinemaId));
        boxOffice.setUser(userService.getUserById(userId).get());
        boxOffice.setPrice(cinemaService.getCinemaById(cinemaId).getPrice());
        if (isUniqueTicket(boxOffice)) {
            boxOfficeRepository.save(boxOffice);
            return true;
        }
        return false;
    }

    public boolean isUniqueTicket(BoxOffice boxOffice) {
        return boxOfficeRepository.getBoxOfficeUnique(boxOffice.getDate(), boxOffice.getTimeSince(),
                boxOffice.getCinema().getId(), boxOffice.getFilm().getId()).isEmpty();
    }

}
