package am.itspace.cinemamodularweb.unit.service;

import am.itspace.cinemamodularcommon.dto.cinemarequestdto.BoxOfficeRequestDTO;
import am.itspace.cinemamodularcommon.entity.cinemadetail.BoxOffice;
import am.itspace.cinemamodularcommon.entity.cinemadetail.Cinema;
import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.filmdetail.Status;
import am.itspace.cinemamodularcommon.entity.userdetail.Role;
import am.itspace.cinemamodularcommon.entity.userdetail.User;
import am.itspace.cinemamodularcommon.mapper.cinemarequestmapper.BoxOfficeRequestMapper;
import am.itspace.cinemamodularcommon.repository.BoxOfficeRepository;
import am.itspace.cinemamodularweb.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BoxOfficeServiceTest {

    @Mock
    private BoxOfficeRepository boxOfficeRepository;
    @Mock
    private BoxOfficeRequestMapper boxOfficeRequestMapper;
    @Mock
    private FilmService filmService;
    @Mock
    private CinemaService cinemaService;
    @Mock
    private UserService userService;
    @Mock
    private TimeSinceService timeSinceService;
    @Mock
    private TimeSince timeSince;
    @Mock
    private BoxOffice boxOffice;
    @Mock
    private BoxOfficeService boxOfficeServiceMock;
    @Mock
    private BoxOfficeRepository boxOfficeRepositoryMock;
    @InjectMocks
    private BoxOfficeService boxOfficeService;

    private final User user = User.builder()
            .id(1)
            .name("some-name")
            .surname("some-surname")
            .email("some@gmailcom")
            .password("some-password")
            .role(Role.USER)
            .pictureUrl("some-picture-name")
            .isEnable(true)
            .token("some-token")
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
            .director(null)
            .actors(null)
            .genres(null)
            .ageLimit("16+")
            .times(null)
            .build();

    private final Cinema cinema = Cinema.builder()
            .id(1)
            .name("test")
            .address("test")
            .phoneNumber("test")
            .email("email")
            .spaciousness(25)
            .pictureUrl("test")
            .films(null)
            .seats(null)
            .price(2500)
            .build();

    private final BoxOfficeRequestDTO boxOfficeRequestDTO = BoxOfficeRequestDTO.builder()
            .timeId(1)
            .price(20.5)
            .userId(1)
            .cinemaId(1)
            .filmId(1)
            .seatingNumber(25)
            .date(LocalDate.now())
            .build();

    private final BoxOffice boxOfficeTest = BoxOffice.builder()
            .id(1)
            .timeSince(LocalTime.now())
            .price(20.5)
            .user(user)
            .cinema(cinema)
            .film(film)
            .seatingNumber(25)
            .date(LocalDate.now())
            .build();

    @Test
    @DisplayName("successfully add ticket")
    void createBoxOfficeTest() {
        when(boxOfficeRequestMapper.map(boxOfficeRequestDTO)).thenReturn(boxOfficeTest);
        lenient().when(boxOfficeServiceMock.isUniqueTicket(boxOfficeTest)).thenReturn(true);
        when(boxOfficeRepository.save(any())).thenReturn(boxOfficeTest);
        when(boxOfficeRequestMapper.map(boxOfficeRequestDTO)).thenReturn(boxOfficeTest);
        when(timeSinceService.getById(1)).thenReturn(timeSince);
        when(filmService.getFilmById(1)).thenReturn(film);
        when(cinemaService.getCinemaById(1)).thenReturn(cinema);
        when(userService.getUserById(1)).thenReturn(Optional.ofNullable(user));
        assertTrue(boxOfficeService.createBoxOffice(boxOfficeRequestDTO, 1, 1, 1));
    }

}