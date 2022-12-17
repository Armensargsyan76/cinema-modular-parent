package am.itspace.cinemamodularrest.service;

import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import am.itspace.cinemamodularcommon.repository.GenreRepository;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public void addGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public List<Genre> findAllGenres() throws EntityNotFoundException {
        if (genreRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("no genres");
        }
        return genreRepository.findAll();
    }

    public Genre getById(int id) throws EntityNotFoundException {
        return genreRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("genre by this id doesn't exist"));
    }

    public void deleteGenreById(int id) throws EntityNotFoundException {
        if (genreRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("genre by this id doesn't exist");
        }
        genreRepository.deleteById(id);
    }

}
