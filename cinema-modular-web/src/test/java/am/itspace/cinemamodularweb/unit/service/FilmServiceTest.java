package am.itspace.cinemamodularweb.unit.service;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.FilmRequestDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import am.itspace.cinemamodularcommon.entity.filmdetail.*;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.FilmRequestMapper;
import am.itspace.cinemamodularcommon.repository.FilmRepository;
import am.itspace.cinemamodularweb.service.FilmService;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {
    @Mock
    private FilmRepository filmRepository;
    @Mock
    private CreatePictureUtil createPictureUtil;
    @Mock
    private FilmRequestMapper filmRequestMapper;
    @Mock
    private MultipartFile multipartFile;
    @Mock
    private Pageable pageable;
    @InjectMocks
    private FilmService filmService;

    private final TimeSince timeSince = TimeSince.builder()
            .id(1)
            .time(LocalTime.now())
            .build();

    private final List<TimeSince> times = List.of(timeSince);
    private final List<Integer> timeListId = List.of(1);

    private final Genre genre = Genre.builder()
            .id(1)
            .name("test")
            .build();

    private final List<Genre> genres = List.of(genre);
    private final List<Integer> genresId = List.of(1);

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
    private final Actor actor1 = Actor.builder()
            .id(2)
            .name("some name")
            .surname("some surname")
            .country("some-country")
            .dateBorn(LocalDate.now())
            .dateDied(null)
            .age(25)
            .pictureUrl("some picture name")
            .biography("some biography")
            .films(null)
            .build();

    private final List<Actor> actors = List.of(actor, actor1);
    private final List<Integer> actorsId = List.of(1, 2);

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

    private final FilmRequestDTO filmRequestDTO = FilmRequestDTO.builder()
            .originalTitle("test")
            .premiere(LocalDate.now())
            .durationMinute(65)
            .description("test")
            .country("test")
            .videoUrl("test")
            .pictureUrl("test")
            .directorId(1)
            .actorsId(actorsId)
            .genresId(genresId)
            .ageLimit("16+")
            .status(Status.ONLINE)
            .timeSinceId(timeListId)
            .build();

    private final Film film = Film.builder()
            .id(1)
            .originalTitle("test")
            .premiere(LocalDate.now())
            .durationMinute(65)
            .description("test")
            .country("test")
            .rating(8.8)
            .videoUrl("test")
            .pictureUrl("test")
            .status(Status.ONLINE)
            .director(director)
            .actors(actors)
            .genres(genres)
            .ageLimit("16+")
            .times(times)
            .build();


    @Test
    @DisplayName("successfully save film")
    void addFilm() {
        String pictureName = "some-picture-name";
        when(filmRequestMapper.map(filmRequestDTO)).thenReturn(film);
        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        when(filmRepository.save(any())).thenReturn(film);
        filmService.addFilm(filmRequestDTO, multipartFile);
        verify(filmRepository, times(1)).save(any());
    }

    @Test
    @DisplayName(" save null")
    void addNull() {
        String pictureName = "some-picture-name";
        when(filmRequestMapper.map(filmRequestDTO)).thenReturn(film);
        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        when(filmRepository.save(any())).thenReturn(film);
        assertThrows(RuntimeException.class, () -> {
            filmService.addFilm(null, multipartFile);
        });
        verify(filmRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("successfully find five films by rating")
    void getFiveFilmsByRatingTest() {
        List<Film> films = new ArrayList<>();
        films.add(film);
        when(filmRepository.findFiveFilmsByRating()).thenReturn(films);
        List<Film> fiveFilmsByRating = filmService.getFiveFilmsByRating();
        assertEquals(fiveFilmsByRating, films);
    }

    @Test
    @DisplayName("successfully count  films")
    void getCountAllFilmsTest() {
        int count = 10;
        when(filmRepository.countAllFilms()).thenReturn(10);
        int countAllFilms = filmService.getCountAllFilms();
        assertEquals(countAllFilms, count);
    }

    @Test
    @DisplayName("successfully get films in page")
    void getAllFilmsPageTest() {
        List<Film> films = new ArrayList<>();
        films.add(film);
        Page<Film> page = new PageImpl<>(films);
        when(filmRepository.findAll(pageable)).thenReturn(page);
        Page<Film> all = filmRepository.findAll(pageable);
        assertEquals(all, page);
    }

    @Test
    @DisplayName("successfully get films sorted by premiere")
    void getFilmsSortedByPremiereTest() {
        List<Film> films = new ArrayList<>();
        films.add(film);
        Page<Film> page = new PageImpl<>(films);
        when(filmRepository.findFilmsSortedByPremiere(pageable)).thenReturn(page);
        Page<Film> all = filmRepository.findFilmsSortedByPremiere(pageable);
        assertEquals(all, page);
    }

    @Test
    @DisplayName("successfully get films sorted by rating")
    void getFilmsSortedByRatingTest() {
        List<Film> films = new ArrayList<>();
        films.add(film);
        Page<Film> page = new PageImpl<>(films);
        when(filmRepository.findFilmSortedByRating(pageable)).thenReturn(page);
        Page<Film> all = filmRepository.findFilmSortedByRating(pageable);
        assertEquals(all, page);
    }

    @Test
    @DisplayName("successfully delete film by id")
    void deleteFilmTrueTest() {
        boolean isDelete = true;
        int id = 1;
        when(filmRepository.findById(id)).thenReturn(Optional.of(film));
        doNothing().when(filmRepository).deleteById(id);
        boolean ok = filmService.deleteFilmById(id);
        assertEquals(isDelete, ok);
    }

    @Test
    @DisplayName("failed delete film by id")
    void deleteFilmFalseTest() {
        boolean isDelete = false;
        int id = 0;
        when(filmRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        boolean fail = filmService.deleteFilmById(id);
        assertEquals(isDelete, fail);
    }

    @Test
    @DisplayName("successfully get last five films")
    void getLastFiveFilmsTest() {
        List<Film> lastFilms = new ArrayList<>();
        lastFilms.add(film);
        when(filmRepository.findFiveLastFilms()).thenReturn(lastFilms);
        List<Film> lastFiveFilms = filmService.getLastFiveFilms();
        assertEquals(lastFiveFilms, lastFilms);
    }

    @Test
    @DisplayName("successfully get film by id")
    void getFilmByIdTest() {
        when(filmRepository.findById(1)).thenReturn(Optional.ofNullable(film));
        Film filmById = filmService.getFilmById(1);
        assertEquals(filmById, film);
    }

    @Test
    @DisplayName("failed get film by id")
    void getFilmByIdFailTest() {
        when(filmRepository.findById(0)).thenReturn(Optional.ofNullable(null));
        Film filmById = filmService.getFilmById(0);
        assertNull(filmById);
    }

    @Test
    @DisplayName("successfully get only cinema films")
    void getOnlyCinemaFilmsTest() {
        List<Film> cinemaFilms = new ArrayList<>();
        cinemaFilms.add(film);
        when(filmRepository.findOnlyCinemaFilms()).thenReturn(cinemaFilms);
        List<Film> onlyCinemaFilms = filmService.getOnlyCinemaFilms();
        assertEquals(cinemaFilms, onlyCinemaFilms);
    }

    @Test
    @DisplayName("successfully get film by genre")
    void getFilmByGenreTest() {
        List<Film> films = new ArrayList<>();
        films.add(film);
        Page<Film> page = new PageImpl<>(films);
        when(filmRepository.findAllByGenres(genre, pageable)).thenReturn(page);
        Page<Film> filmByGenre = filmService.getFilmByGenre(genre, pageable);
        assertEquals(filmByGenre, page);
    }

    @Test
    @DisplayName("successfully get film by premiere")
    void getFilmByPremiereTest() {
        List<Film> films = new ArrayList<>();
        films.add(film);
        when(filmRepository.findAllByPremiereYear(2005, 2010)).thenReturn(films);
        List<Film> filmByPremiere = filmService.getFilmByPremiere(2005, 2010);
        assertEquals(films, filmByPremiere);
    }

}