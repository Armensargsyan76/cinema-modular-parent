package am.itspace.cinemamodularrest.service;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.ActorUpdateRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmresponsedto.ActorResponseDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Actor;
import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.ActorRequestMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.ActorResponseMapper;
import am.itspace.cinemamodularcommon.repository.ActorRepository;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import am.itspace.cinemamodularrest.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final CreatePictureUtil createPictureUtil;
    private final ActorRequestMapper actorRequestMapper;
    private final ActorResponseMapper actorResponseMapper;

    public void addActor(Actor actor, MultipartFile multipartFile) {
        if (!multipartFile.isEmpty() && multipartFile.getSize() > 0) {
            actor.setPictureUrl(createPictureUtil.creatPicture(multipartFile));
        }
        actor.setAge(calculateAge(actor.getDateBorn()));
        actorRepository.save(actor);
    }

    public Actor getById(int id) throws EntityNotFoundException {
        return actorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("actor by this id doesn't exist"));
    }

    public List<Actor> findAllActors() throws EntityNotFoundException {
        if (actorRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("no actors");
        }
        return actorRepository.findAll();
    }

    public void deleteActorById(int id) throws EntityNotFoundException {
        if (actorRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("actor by this id doesn't exist");
        }
        actorRepository.deleteById(id);
    }

    public int calculateAge(LocalDate localDate) {
        return LocalDate.now().getYear() - localDate.getYear();
    }

    public ActorResponseDTO updateActor(int id, ActorUpdateRequestDTO actorUpdateRequestDTO) throws EntityNotFoundException {
        Optional<Actor> optionalActor = actorRepository.findById(id);
        if (optionalActor.isEmpty()) {
            throw new EntityNotFoundException("actor by this id doesn't exist");
        }
        Actor actor = actorRequestMapper.map(actorUpdateRequestDTO);
        actor.setId(id);
        return actorResponseMapper.map(actorRepository.save(actor));
    }

    public List<Film> getFilmByActor(int actorId) {
        Optional<Actor> optionalActor = actorRepository.findById(actorId);
        if (optionalActor.isEmpty()) {
            return new ArrayList<>();
        }
        return optionalActor.get().getFilms();
    }

}
