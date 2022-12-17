package am.itspace.cinemamodularweb.unit.service;

import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.ActorRequestMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.ActorResponseMapper;
import am.itspace.cinemamodularcommon.repository.ActorRepository;
import am.itspace.cinemamodularweb.service.ActorService;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {
    @Mock
    private ActorResponseMapper actorResponseMapper;
    @Mock
    private ActorRequestMapper actorRequestMapper;
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private CreatePictureUtil createPictureUtil;
    @InjectMocks
    private ActorService actorService;

    @Test
    @DisplayName("successfully calculate age")
    void calculateAge() {
        int age = 24;

        int actualAge = actorService.calculateAge(LocalDate.now().minusYears(24));

        assertEquals(age, actualAge);
    }
}