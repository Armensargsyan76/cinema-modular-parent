package am.itspace.cinemamodularweb.controller.cinemadetailcontroller;

import am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper.CinemaResponseMapper;
import am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper.CinemaSeatResponseMapper;
import am.itspace.cinemamodularcommon.mapper.cinemaresponsemapper.TimeSinceResponseMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.FilmResponseMapper;
import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularweb.security.CurrentUser;
import am.itspace.cinemamodularweb.service.CinemaSeatService;
import am.itspace.cinemamodularweb.service.CinemaService;
import am.itspace.cinemamodularweb.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CinemaPageController {

    private final UserMapper userMapper;
    private final CinemaService cinemaService;
    private final FilmService filmService;
    private final FilmResponseMapper filmResponseMapper;
    private final TimeSinceResponseMapper timeSinceResponseMapper;
    private final CinemaResponseMapper cinemaResponseMapper;
    private final CinemaSeatResponseMapper cinemaSeatResponseMapper;
    private final CinemaSeatService cinemaSeatService;

    @GetMapping("/cinema/page/{id}")
    public String cinemaPage(@PathVariable("id") int id,
                             @AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {

        modelMap.addAttribute("cinema", cinemaResponseMapper.map(cinemaService.getCinemaById(id)));
        modelMap.addAttribute("user", userMapper.map(currentUser.getUser()));
        modelMap.addAttribute("films", filmResponseMapper.map(cinemaService.getCinemaById(id).getFilms()));
        return "cinema/cinemaPage";
    }

    @GetMapping("/get/choose/film/{id}/{cinemaId}")
    public String getChooseFilm(@PathVariable("id") int id, @PathVariable("cinemaId") int cinemaId, ModelMap modelMap,
                                @AuthenticationPrincipal CurrentUser currentUser) {

        modelMap.addAttribute("cinema", cinemaResponseMapper.map(cinemaService.getCinemaById(cinemaId)));
        modelMap.addAttribute("seats", cinemaSeatResponseMapper.map(cinemaSeatService.getSeatsByCinemaId(cinemaId)));
        modelMap.addAttribute("user", userMapper.map(currentUser.getUser()));
        modelMap.addAttribute("chooseFilm", filmResponseMapper.map(filmService.getFilmById(id)));
        modelMap.addAttribute("timeSinceChooseFilm",
                timeSinceResponseMapper.map(filmResponseMapper.map(filmService.getFilmById(id)).getTimes()));
        return "cinema/cinemaPageChooseFilm";
    }

}
