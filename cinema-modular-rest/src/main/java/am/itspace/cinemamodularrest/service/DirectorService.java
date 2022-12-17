package am.itspace.cinemamodularrest.service;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.DirectorUpdateRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmresponsedto.DirectorResponseDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Director;
import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.DirectorRequestMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.DirectorResponseMapper;
import am.itspace.cinemamodularcommon.repository.DirectorRepository;
import am.itspace.cinemamodularcommon.repository.FilmRepository;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final DirectorRequestMapper directorRequestMapper;
    private final DirectorResponseMapper directorResponseMapper;
    private final FilmRepository filmRepository;

    public void addDirector(Director director) {
        director.setAge(calculateAge(director.getDateBorn()));
        directorRepository.save(director);
    }

    public Director getById(int id) throws EntityNotFoundException {
        return directorRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException("director by this id doesn't exist"));
    }

    public List<Director> findAllActors() throws EntityNotFoundException {
        if (directorRepository.findAll().isEmpty()){
            throw new EntityNotFoundException("no directors");
        }
        return directorRepository.findAll();
    }

    public void deleteDirectorById(int id) throws EntityNotFoundException {
        if (directorRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("director by this id doesn't exist");
        }
        directorRepository.deleteById(id);
    }

    public DirectorResponseDTO updateDirector(int id, DirectorUpdateRequestDTO directorUpdateRequestDTO) throws EntityNotFoundException {
        Optional<Director> optionalDirector = directorRepository.findById(id);
        if (optionalDirector.isEmpty()) {
            throw new EntityNotFoundException("director by this id doesn't exist");
        }
        Director director = directorRequestMapper.map(directorUpdateRequestDTO);
        director.setId(id);
        return directorResponseMapper.map(directorRepository.save(director));
    }

    public List<Film> getFilmsByDirector(int directorId){
       if (filmRepository.findAllByDirectorId(directorId).isEmpty()){
           return new ArrayList<>();
       }
       return filmRepository.findAllByDirectorId(directorId);
    }

    public int calculateAge(LocalDate localDate) {
        return LocalDate.now().getYear() - localDate.getYear();
    }

}
