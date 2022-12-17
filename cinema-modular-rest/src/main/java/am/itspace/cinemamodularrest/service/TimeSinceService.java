package am.itspace.cinemamodularrest.service;

import am.itspace.cinemamodularcommon.entity.cinemadetail.TimeSince;
import am.itspace.cinemamodularcommon.repository.TimeSinceRepository;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSinceService {

    private final TimeSinceRepository timeSinceRepository;

    public void addTimeSince(TimeSince timeSince) {
        timeSinceRepository.save(timeSince);
    }

    public List<TimeSince> findAllTimeSince() throws EntityNotFoundException {
        if (timeSinceRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("no time since");
        }
        return timeSinceRepository.findAll();
    }

    public TimeSince getById(int id) throws EntityNotFoundException {
        return timeSinceRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("time since by this id doesn't exist"));
    }

    public void deleteTimeSinceById(int id) throws EntityNotFoundException {
        if (timeSinceRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("time since by this id doesn't exist");
        }
        timeSinceRepository.deleteById(id);
    }

}
