package am.itspace.cinemamodularrest.endpoint;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.DirectorRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmrequestdto.DirectorUpdateRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmresponsedto.DirectorResponseDTO;
import am.itspace.cinemamodularcommon.dto.filmresponsedto.FilmResponseDTO;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.DirectorRequestMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.DirectorResponseMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.FilmResponseMapper;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import am.itspace.cinemamodularrest.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorEndpoint {

    private final DirectorService directorService;
    private final DirectorRequestMapper directorRequestMapper;
    private final DirectorResponseMapper directorResponseMapper;
    private final FilmResponseMapper filmResponseMapper;

    @PostMapping()
    public ResponseEntity<DirectorResponseDTO> addDirector(@RequestBody @Valid DirectorRequestDTO directorRequestDTO) {
        directorService.addDirector(directorRequestMapper.map(directorRequestDTO));
        return ResponseEntity.ok(directorResponseMapper.map(directorRequestMapper.map(directorRequestDTO)));
    }

    @GetMapping()
    public ResponseEntity<List<DirectorResponseDTO>> getAllActors() throws EntityNotFoundException {
        return ResponseEntity.ok(directorResponseMapper.map(directorService.findAllActors()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> getActorById(@PathVariable("id") int id) throws EntityNotFoundException {
        return ResponseEntity.ok(directorResponseMapper.map(directorService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActorById(@PathVariable("id") int id) throws EntityNotFoundException {
        directorService.deleteDirectorById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<List<FilmResponseDTO>> getFilmsByDirector(@PathVariable("id") int id) {
        return ResponseEntity.ok(filmResponseMapper.map(directorService.getFilmsByDirector(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> updateDirectorById(@PathVariable("id") int id,
                                                            @RequestBody DirectorUpdateRequestDTO updateDTO) throws EntityNotFoundException {
        return ResponseEntity.ok(directorService.updateDirector(id, updateDTO));
    }

}
