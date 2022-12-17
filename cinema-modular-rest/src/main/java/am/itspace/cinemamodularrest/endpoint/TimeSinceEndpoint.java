package am.itspace.cinemamodularrest.endpoint;

import am.itspace.cinemamodularcommon.dto.cinemarequestdto.TimeSinceRequestDTO;
import am.itspace.cinemamodularcommon.dto.cinemaresponsedto.TimeSinceResponseDTO;
import am.itspace.cinemamodularcommon.mapper.cinemarequestmapper.TimeSinceRequestMapper;
import am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper.TimeSinceResponseMapper;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import am.itspace.cinemamodularrest.service.TimeSinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/time/since")
public class TimeSinceEndpoint {

    private final TimeSinceService timeSinceService;
    private final TimeSinceRequestMapper timeSinceRequestMapper;
    private final TimeSinceResponseMapper timeSinceResponseMapper;

    @PostMapping
    public ResponseEntity<TimeSinceResponseDTO> addTimeSince(@RequestBody @Valid TimeSinceRequestDTO timeSinceRequestDTO) {
        timeSinceService.addTimeSince(timeSinceRequestMapper.map(timeSinceRequestDTO));
        return ResponseEntity.ok(timeSinceResponseMapper.map(timeSinceRequestMapper.map(timeSinceRequestDTO)));
    }

    @GetMapping
    public ResponseEntity<List<TimeSinceResponseDTO>> getAllTimeSince() throws EntityNotFoundException {
        return ResponseEntity.ok(timeSinceResponseMapper.map(timeSinceService.findAllTimeSince()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSinceResponseDTO> getTimeSinceById(@PathVariable("id") int id) throws EntityNotFoundException {
        return ResponseEntity.ok(timeSinceResponseMapper.map(timeSinceService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimeSinceById(@PathVariable("id") int id) throws EntityNotFoundException {
        timeSinceService.deleteTimeSinceById(id);
        return ResponseEntity.noContent().build();
    }

}
