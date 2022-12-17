package am.itspace.cinemamodularrest.controller;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import am.itspace.cinemamodularrest.service.FilmService;
import am.itspace.cinemamodularrest.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final CreatePictureUtil createPictureUtil;


    @GetMapping("/films/genre")
    public ResponseEntity<List<Film>> getFilmsByGenre(@ModelAttribute Genre genre) {
        List<Film> filmByGenre = filmService.getFilmByGenre(genre);
        return ResponseEntity.ok(filmByGenre);
    }


    @GetMapping("/films/year")
    public ResponseEntity<List<Film>> getByYear(@RequestParam(name = "minYear") int minYear,
                                                @RequestParam(name = "maxYear") int maxYear) {
        List<Film> filmByPremiere = filmService.getFilmByPremiere(minYear, maxYear);
        return ResponseEntity.ok(filmByPremiere);
    }

    @GetMapping("/films/rating")
    public ResponseEntity<List<Film>> getByRating() {
        List<Film> byRating = filmService.getByRating();
        return ResponseEntity.ok(byRating);

    }


    @DeleteMapping("/film/delete/{id}")
    public ResponseEntity<?> deleteFilmById(@PathVariable("id") int id) {
        if (filmService.deleteFilmById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/film/image")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String fileName) {
        if (createPictureUtil.getImage(fileName) == null) {
            return null;
        }
        return createPictureUtil.getImage(fileName);
    }

}
