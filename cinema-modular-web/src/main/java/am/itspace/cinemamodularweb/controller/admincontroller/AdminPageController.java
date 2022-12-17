package am.itspace.cinemamodularweb.controller.admincontroller;

import am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper.TimeSinceResponseMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.ActorResponseMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.DirectorResponseMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.GenreResponseMapper;
import am.itspace.cinemamodularweb.service.*;
import am.itspace.cinemamodularweb.util.CreatePaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final FilmService filmService;
    private final GenreService genreService;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final UserService userService;
    private final CreatePaginationUtil createPaginationUtil;
    private final DirectorResponseMapper directorResponseMapper;
    private final ActorResponseMapper actorResponseMapper;
    private final GenreResponseMapper genreResponseMapper;
    private final CinemaService cinemaService;
    private final TimeSinceService timeSinceService;
    private final TimeSinceResponseMapper timeSinceResponseMapper;

    @GetMapping("/admin/page")
    public String adminPage() {
        return "redirect:/user/profile";
    }

    @GetMapping("/add/film")
    public String addFilmsPage(ModelMap modelMap) {
        modelMap.addAttribute("directors", directorResponseMapper.map(directorService.findAllDirectors()));
        modelMap.addAttribute("actors", actorResponseMapper.map(actorService.findAllActors()));
        modelMap.addAttribute("genres", genreResponseMapper.map(genreService.findAllGenres()));
        modelMap.addAttribute("times", timeSinceResponseMapper.map(timeSinceService.findAllTimeSince()));
        return "admin/addFilm";
    }

    @GetMapping("/add/film-in/cinema")
    public String addFilmInCinema(ModelMap modelMap) {
        modelMap.addAttribute("cinemas", cinemaService.getAllCinemas());
        modelMap.addAttribute("films", filmService.getOnlyCinemaFilms());
        return "admin/addFilmInCinema";
    }

    @GetMapping("/add/cinema/page")
    public String addCinemaPage(ModelMap modelMap) {
        modelMap.addAttribute("cinemas", cinemaService.getAllCinemas());
        return "admin/addCinema";
    }

    @GetMapping("/catalog/films")
    public String CatalogFilmsPage(@RequestParam("page") Optional<Integer> page, ModelMap modelMap,
                                   @RequestParam("size") Optional<Integer> size) {

        modelMap.addAttribute("allFilms",
                filmService.getAllFilms(PageRequest.of(page.orElse(1) - 1, size.orElse(10))));
        modelMap.addAttribute("pageNumbers",
                createPaginationUtil.createPageNumbers(filmService.getAllFilms(PageRequest.of(page.orElse(1) - 1, size.orElse(10))).getTotalPages()));
        modelMap.addAttribute("countAllFilms", filmService.getCountAllFilms());
        return "admin/catalogFilms";
    }

    @GetMapping("/films/premiere")
    public String getFilmsByDate(@RequestParam("page") Optional<Integer> page, ModelMap modelMap,
                                 @RequestParam("size") Optional<Integer> size) {

        modelMap.addAttribute("allFilms",
                filmService.getFilmsSortedByPremiere(PageRequest.of(page.orElse(1) - 1, size.orElse(10))));
        modelMap.addAttribute("pageNumbers",
                createPaginationUtil.createPageNumbers(filmService.getFilmsSortedByPremiere(PageRequest.of(page.orElse(1) - 1, size.orElse(10))).getTotalPages()));
        modelMap.addAttribute("countAllFilms", filmService.getCountAllFilms());
        return "admin/catalogFilms";
    }

    @GetMapping("/get/films/rating")
    public String getFilmsByRating(@RequestParam("page") Optional<Integer> page, ModelMap modelMap,
                                   @RequestParam("size") Optional<Integer> size) {

        modelMap.addAttribute("allFilms",
                filmService.getFilmsSortedByRating(PageRequest.of(page.orElse(1) - 1, size.orElse(10))));
        modelMap.addAttribute("pageNumbers",
                createPaginationUtil.createPageNumbers(filmService.getFilmsSortedByRating(PageRequest.of(page.orElse(1) - 1, size.orElse(10))).getTotalPages()));
        modelMap.addAttribute("countAllFilms", filmService.getCountAllFilms());
        return "admin/catalogFilms";
    }

    @GetMapping("/users/all")
    public String allUsersPage(@RequestParam("page") Optional<Integer> page, ModelMap modelMap,
                               @RequestParam("size") Optional<Integer> size) {

        modelMap.addAttribute("countAllUsers", userService.getCountAllUsers());
        modelMap.addAttribute("allUsers",
                userService.getAllUsers(PageRequest.of(page.orElse(1) - 1, size.orElse(10))));
        modelMap.addAttribute("pageNumbers", createPaginationUtil.createPageNumbers
                (userService.getAllUsers(PageRequest.of(page.orElse(1) - 1, size.orElse(10))).getTotalPages()));
        return "admin/allUsers";
    }

}
