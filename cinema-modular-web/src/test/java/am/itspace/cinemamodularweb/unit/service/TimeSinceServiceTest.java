package am.itspace.cinemamodularweb.unit.service;

import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import am.itspace.cinemamodularcommon.repository.TimeSinceRepository;
import am.itspace.cinemamodularweb.service.TimeSinceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeSinceServiceTest {

    @Mock
    private TimeSinceRepository timeSinceRepository;
    @InjectMocks
    private TimeSinceService timeSinceService;

    private final TimeSince timeSince = TimeSince.builder()
            .id(1)
            .time(LocalTime.now())
            .build();

    private final List<TimeSince> timeSinceList = List.of(timeSince);

    @Test
    @DisplayName("successfully find all time since")
    void findAllTimeSince(){
        when(timeSinceRepository.findAll()).thenReturn(timeSinceList);
        assertEquals(timeSinceService.findAllTimeSince(), timeSinceList);
    }

    @Test
    @DisplayName("failed find all time since")
    void findAllTimeSinceFailTest() {
        when(timeSinceRepository.findAll()).thenReturn(null);
        assertNull(timeSinceService.findAllTimeSince());
    }

    @Test
    @DisplayName("successfully find time since by id")
    void getByIdTest(){
        when(timeSinceRepository.findById(1)).thenReturn(Optional.of(timeSince));
        assertEquals(timeSinceService.getById(1), timeSince);
    }

    @Test
    @DisplayName("failed get time since by id")
    void getByIdFailTest() {
        when(timeSinceRepository.findById(0)).thenReturn(Optional.empty());
        assertNull(timeSinceService.getById(0));
    }

}