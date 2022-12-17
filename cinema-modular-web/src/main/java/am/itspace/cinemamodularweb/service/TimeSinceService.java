package am.itspace.cinemamodularweb.service;

import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import am.itspace.cinemamodularcommon.repository.TimeSinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSinceService {

    private final TimeSinceRepository timeSinceRepository;

    public List<TimeSince> findAllTimeSince() {
        return timeSinceRepository.findAll();
    }

    public TimeSince getById(int id) {
        return timeSinceRepository.findById(id).orElse(null);
    }

}
