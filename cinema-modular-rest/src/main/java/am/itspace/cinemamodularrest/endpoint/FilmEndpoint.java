package am.itspace.cinemamodularrest.endpoint;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.FilmRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmresponsedto.FilmResponseDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.FilmRequestMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.FilmResponseMapper;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import am.itspace.cinemamodularrest.service.FilmService;
import am.itspace.cinemamodularrest.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmEndpoint {

    private final FilmService filmService;
    private final CreatePictureUtil createPictureUtil;
    private final FilmResponseMapper filmResponseMapper;

    @PostMapping("/add")
    public ResponseEntity<FilmResponseDTO> addFilm(@RequestBody @Valid FilmRequestDTO filmRequestDTO) throws EntityNotFoundException {
        return ResponseEntity.ok(filmResponseMapper.map(filmService.addFilm(filmRequestDTO)));
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Film>> getFilmsByGenre(@ModelAttribute Genre genre) {
        List<Film> filmByGenre = filmService.getFilmByGenre(genre);
        return ResponseEntity.ok(filmByGenre);
    }


    @GetMapping("/year")
    public ResponseEntity<List<Film>> getByYear(@RequestParam(name = "minYear") int minYear,
                                                @RequestParam(name = "maxYear") int maxYear) {
        List<Film> filmByPremiere = filmService.getFilmByPremiere(minYear, maxYear);
        return ResponseEntity.ok(filmByPremiere);
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Film>> getByRating() {
        List<Film> byRating = filmService.getByRating();
        return ResponseEntity.ok(byRating);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFilmById(@PathVariable("id") int id) {
        if (filmService.deleteFilmById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/image", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] getImage(@RequestParam("picName") String fileName) {
        if (createPictureUtil.getImage(fileName) == null) {
            return null;
        }
        return createPictureUtil.getImage(fileName);
    }

}
