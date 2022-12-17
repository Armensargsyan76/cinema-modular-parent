package am.itspace.cinemamodularweb.service;

import am.itspace.cinemamodularcommon.entity.filmdetail.Actor;
import am.itspace.cinemamodularcommon.repository.ActorRepository;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class
ActorService {

    private final ActorRepository actorRepository;
    private final CreatePictureUtil createPictureUtil;

    public void addActor(Actor actor, MultipartFile multipartFile) {
        if (!multipartFile.isEmpty() && multipartFile.getSize() > 0) {
            actor.setPictureUrl(createPictureUtil.creatPicture(multipartFile));
        }
        actor.setAge(calculateAge(actor.getDateBorn()));
        actorRepository.save(actor);
    }

    public Actor getById(int id) {
        return actorRepository.findById(id).orElse(null);
    }

    public List<Actor> findAllActors() {
        return actorRepository.findAll();
    }

    public int calculateAge(LocalDate localDate) {
        return LocalDate.now().getYear() - localDate.getYear();
    }

}
