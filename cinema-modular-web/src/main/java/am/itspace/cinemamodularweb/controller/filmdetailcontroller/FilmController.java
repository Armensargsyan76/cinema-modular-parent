package am.itspace.cinemamodularweb.controller.filmdetailcontroller;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.FilmRequestDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.filmdetail.Genre;
import am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper.TimeSinceResponseMapper;
import am.itspace.cinemamodularweb.service.*;
import am.itspace.cinemamodularweb.util.CheckImportedData;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class FilmController {

    private final GenreService genreService;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final CheckImportedData checkImportedData;
    private final FilmService filmService;
    private final TimeSinceService timeSinceService;
    private final TimeSinceResponseMapper timeSinceResponseMapper;
    private final CreatePictureUtil createPictureUtil;

    @GetMapping("/add/actor")
    public String addActorPage() {
        return "/admin/addActor";
    }

    @GetMapping("/add/director")
    public String addDirectorPage() {
        return "/admin/addDirector";
    }

    @GetMapping("/film/image")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String fileName) {
        if (createPictureUtil.getImage(fileName) == null) {
            return null;
        }
        return createPictureUtil.getImage(fileName);
    }

    @GetMapping("/film")
    public String allFilmsPage(ModelMap modelMap) {
        modelMap.addAttribute("genres", genreService.findAllGenres());
        return "main/allFilmsPage";
    }

    @GetMapping("/films/genre")
    public String filmsPage(@ModelAttribute Genre genre,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            ModelMap modelMap) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Film> byGenre = filmService.getFilmByGenre(genre, PageRequest.of(currentPage - 1, pageSize));
        modelMap.addAttribute("films", byGenre);
        PageRequest.of(currentPage - 1, pageSize);
        int totalPages = byGenre.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        return "main/allFilmsPage";
    }

    @GetMapping("/films/year")
    public String getByYear(@RequestParam(name = "minYear") int minYear,
                            @RequestParam(name = "maxYear") int maxYear,
                            ModelMap modelMap) {
        List<Film> filmByPremiere = filmService.getFilmByPremiere(minYear, maxYear);
        modelMap.addAttribute("films", filmByPremiere);
        return "main/allFilmsPage";
    }

    @GetMapping("/films/rating")
    public String getByRating(ModelMap modelMap) {

        List<Film> byRating = filmService.getByRating();
        modelMap.addAttribute("films", byRating);
        return "main/allFilmsPage";

    }

    @PostMapping("/add/film")
    public String addFilm(@ModelAttribute @Valid FilmRequestDTO filmRequestDTO, BindingResult bindingResult,
                          @RequestParam("imageFilm") MultipartFile multipartFile, ModelMap modelMap) {

        if (checkImportedData.checkData(bindingResult, multipartFile, modelMap).isPresent()) {
            checkImportedData.checkData(bindingResult, multipartFile, modelMap).get();
            modelMap.addAttribute("directors", directorService.findAllDirectors());
            modelMap.addAttribute("actors", actorService.findAllActors());
            modelMap.addAttribute("genres", genreService.findAllGenres());
            modelMap.addAttribute("times", timeSinceResponseMapper.map(timeSinceService.findAllTimeSince()));
            return "admin/addFilm";
        }
        filmService.addFilm(filmRequestDTO, multipartFile);
        return "redirect:/add/film";
    }

    @GetMapping("/film/delete/{id}")
    public String deleteFilmById(@PathVariable("id") int id, ModelMap modelMap, Pageable pageable) {
        if (filmService.deleteFilmById(id)) {
            return "redirect:/catalog/films";
        }
        modelMap.addAttribute("countAllFilms", filmService.getCountAllFilms());
        modelMap.addAttribute("allFilms", filmService.getAllFilms(pageable));
        modelMap.addAttribute("idNotExist", "id not exist");
        return "admin/catalogFilms";
    }

}
