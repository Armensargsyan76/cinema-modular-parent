package am.itspace.cinemamodularweb.unit.service;

import am.itspace.cinemamodularcommon.entity.filmdetail.Actor;
import am.itspace.cinemamodularcommon.repository.ActorRepository;
import am.itspace.cinemamodularweb.service.ActorService;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;
    @Mock
    private CreatePictureUtil createPictureUtil;
    @Mock
    private MultipartFile multipartFile;
    @InjectMocks
    private ActorService actorService;

    private final Actor actor = Actor.builder()
            .id(1)
            .name("some-name")
            .surname("some-surname")
            .country("some-country")
            .dateBorn(LocalDate.now())
            .dateDied(null)
            .age(20)
            .pictureUrl("some-picture-name")
            .biography("some-biography")
            .films(null)
            .build();

    private final List<Actor> actors = new ArrayList<>();

    @Test
    @DisplayName("successfully add actor")
    void addActor(){
        String pictureName = "some-picture-name";
        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        when(actorRepository.save(actor)).thenReturn(actor);
        actorService.addActor(actor, multipartFile);
    }

    @Test
    void addNull(){
        String pictureName = "some-picture-name";
        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        assertThrows(RuntimeException.class, ()-> {
            actorService.addActor(null, multipartFile);
        });
        verify(actorRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("successfully find actor by id")
    void getById(){
        when(actorRepository.findById(1)).thenReturn(Optional.of(actor));
        assertEquals(actorService.getById(1), actor);
    }

    @Test
    @DisplayName("failed get actor by id")
    void getActorByIdFailTest() {
        when(actorRepository.findById(0)).thenReturn(Optional.empty());
        assertNull(actorService.getById(0));
    }

    @Test
    @DisplayName("successfully find all actors ")
    void findAllActorsTest(){
        actors.add(actor);
        when(actorRepository.findAll()).thenReturn(actors);
        assertEquals(actorService.findAllActors(), actors);
    }

    @Test
    @DisplayName("failed find all actors")
    void findAllActorsFailTest(){
        when(actorRepository.findAll()).thenReturn(null);
        assertNull(actorService.findAllActors());
    }

    @Test
    @DisplayName("successfully calculate age")
    void calculateAge() {
        int age = 24;
        int actualAge = actorService.calculateAge(LocalDate.now().minusYears(24));
        assertEquals(age, actualAge);
    }

}
























