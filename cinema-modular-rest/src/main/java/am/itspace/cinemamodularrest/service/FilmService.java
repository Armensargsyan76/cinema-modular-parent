package am.itspace.cinemamodularrest.service;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import am.itspace.cinemamodularcommon.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmRepository filmRepository;


    public List<Film> getFilmByGenre(Genre genre) {
        return filmRepository.findAllByGenres(genre);
    }

    public List<Film> getFilmByPremiere(int minDate, int maxDate) {
        return filmRepository.findAllByPremiere_Year(minDate, maxDate);
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
}
