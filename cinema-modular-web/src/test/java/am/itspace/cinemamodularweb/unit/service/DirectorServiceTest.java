package am.itspace.cinemamodularweb.unit.service;

import am.itspace.cinemamodularcommon.entity.filmdetail.Director;
import am.itspace.cinemamodularcommon.repository.DirectorRepository;
import am.itspace.cinemamodularweb.service.DirectorService;
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
class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;
    @Mock
    private CreatePictureUtil createPictureUtil;
    @Mock
    private MultipartFile multipartFile;
    @InjectMocks
    private DirectorService directorService;

    private final Director director = Director.builder()
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

    @Test
    @DisplayName("successfully add director")
    void addDirectorTest(){
        String pictureName = "some-picture-name";
        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        when(directorRepository.save(director)).thenReturn(director);
        directorService.addDirector(director, multipartFile);
    }

    @Test
    void addNull(){
        String pictureName = "some-picture-name";
        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        assertThrows(RuntimeException.class, ()-> {
            directorService.addDirector(null, multipartFile);
        });
        verify(directorRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("successfully find director by id")
    void getByIdTest(){
        when(directorRepository.findById(1)).thenReturn(Optional.of(director));
        Director byId = directorService.findById(1);
        assertEquals(byId, director);
    }

    @Test
    @DisplayName("failed get director by id")
    void getDirectorByIdFailTest() {
        when(directorRepository.findById(0)).thenReturn(Optional.ofNullable(null));
        Director directorById = directorService.findById(0);
        assertNull(directorById);
    }

    @Test
    @DisplayName("successfully find all directors")
    void findAllDirectorsTest(){
        List<Director> directors = new ArrayList<>();
        directors.add(director);
        when(directorRepository.findAll()).thenReturn(directors);
        List<Director> allDirectors = directorService.findAllDirectors();
        assertEquals(allDirectors, directors);
    }

    @Test
    @DisplayName("failed find all directors")
    void findAllDirectorsFailTest(){
        when(directorRepository.findAll()).thenReturn(null);
        List<Director> allDirectors = directorService.findAllDirectors();
        assertNull(allDirectors);
    }

    @Test
    @DisplayName("successfully calculate age")
    void calculateAgeTest() {
        int age = 24;
        int actualAge = directorService.calculateAge(LocalDate.now().minusYears(24));
        assertEquals(age, actualAge);
    }


















}