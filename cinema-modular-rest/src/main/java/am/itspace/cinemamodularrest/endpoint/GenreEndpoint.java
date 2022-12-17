package am.itspace.cinemamodularrest.endpoint;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.GenreRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmresponsedto.GenreResponseDTO;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.GenreRequestMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.GenreResponseMapper;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import am.itspace.cinemamodularrest.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreEndpoint {

    private final GenreService genreService;
    private final GenreRequestMapper genreRequestMapper;
    private final GenreResponseMapper genreResponseMapper;

    @PostMapping("/add")
    public ResponseEntity<GenreResponseDTO> addGenre(@RequestBody @Valid GenreRequestDTO genreRequestDTO) {
        genreService.addGenre(genreRequestMapper.map(genreRequestDTO));
        return ResponseEntity.ok(genreResponseMapper.map(genreRequestMapper.map(genreRequestDTO)));
    }

    @GetMapping("/get")
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres() throws EntityNotFoundException {
        return ResponseEntity.ok(genreResponseMapper.map(genreService.findAllGenres()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GenreResponseDTO> getGenreById(@PathVariable("id") int id) throws EntityNotFoundException {
        return ResponseEntity.ok(genreResponseMapper.map(genreService.getById(id)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGenreById(@PathVariable("id") int id) throws EntityNotFoundException {
        genreService.deleteGenreById(id);
        return ResponseEntity.noContent().build();
    }

}
