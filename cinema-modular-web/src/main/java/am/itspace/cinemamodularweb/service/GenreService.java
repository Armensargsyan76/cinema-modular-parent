package am.itspace.cinemamodularweb.service;

import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import am.itspace.cinemamodularcommon.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getById(int id) {
        return genreRepository.findById(id).orElse(null);
    }

}
