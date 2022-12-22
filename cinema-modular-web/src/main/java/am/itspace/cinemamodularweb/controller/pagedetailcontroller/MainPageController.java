package am.itspace.cinemamodularweb.controller.pagedetailcontroller;

import am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper.CinemaResponseMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.FilmResponseMapper;
import am.itspace.cinemamodularweb.service.CinemaService;
import am.itspace.cinemamodularweb.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final FilmService filmService;
    private final FilmResponseMapper filmResponseMapper;
    private final CinemaResponseMapper cinemaResponseMapper;
    private final CinemaService cinemaService;

    @GetMapping("/")
    public String mainPage(ModelMap modelMap) {
        modelMap.addAttribute("films", filmResponseMapper.map(filmService.getLastFiveFilms()));
        modelMap.addAttribute("cinemas", cinemaResponseMapper.map(cinemaService.getAllCinemas()));
        return "main/mainHome";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "main/about";
    }

}
