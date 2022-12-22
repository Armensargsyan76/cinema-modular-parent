package am.itspace.cinemamodularrest.service;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.FilmRequestDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import am.itspace.cinemamodularcommon.entity.filmdetail.Actor;
import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import am.itspace.cinemamodularcommon.entity.filmdetail.Status;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.FilmRequestMapper;
import am.itspace.cinemamodularcommon.repository.FilmRepository;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmRepository filmRepository;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final TimeSinceService timeSinceService;
    private final FilmRequestMapper filmRequestMapper;

    public Film addFilm(FilmRequestDTO filmRequestDTO) throws EntityNotFoundException {
        Film film = filmRequestMapper.map(filmRequestDTO);
        film.setActors(allActorsById(filmRequestDTO.getActorsId()));
        film.setDirector(directorService.getById(filmRequestDTO.getDirectorId()));
        film.setGenres(allGenresById(filmRequestDTO.getGenresId()));
        if (film.getStatus() == Status.IN_CINEMA) {
            film.setTimes(allTimeSinceId(filmRequestDTO.getTimeSinceId()));
        }
        filmRepository.save(film);
        log.info("film added {}", film.getOriginalTitle());
        return film;
    }

    public List<Film> getFilmByGenre(Genre genre) {
        return filmRepository.findAllByGenres(genre);
    }

    public List<Film> getFilmByPremiere(int minDate, int maxDate) {
        return filmRepository.findAllByPremiereYear(minDate, maxDate);
    }

    public List<Film> getByRating() {
        return filmRepository.findAllByRating();
    }

    public boolean deleteFilmById(int id) {
        if (filmRepository.findById(id).isPresent()) {
            filmRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private List<Genre> allGenresById(List<Integer> genresIds) throws EntityNotFoundException {
        var genres = new ArrayList<Genre>();
        for (Integer genreId : genresIds) {
            if (genreId
                    != 0) {
                genres.add(genreService.getById(genreId));
            }
        }
        return genres;
    }

    private List<Actor> allActorsById(List<Integer> actorsIds) throws EntityNotFoundException {
        var actors = new ArrayList<Actor>();
        for (Integer actorId : actorsIds) {
            if (actorId != 0) {
                actors.add(actorService.getById(actorId));
            }
        }
        return actors;
    }

    private List<TimeSince> allTimeSinceId(List<Integer> timeSinceId) throws EntityNotFoundException {
        var timeSince = new ArrayList<TimeSince>();
        for (Integer timeId : timeSinceId) {
            if (timeId != 0) {
                timeSince.add(timeSinceService.getById(timeId));
            }
        }
        return timeSince;
    }

}
