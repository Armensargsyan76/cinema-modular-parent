package am.itspace.cinemamodularweb.controller.filmdetailcontroller;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.FilmResponseMapper;
import am.itspace.cinemamodularweb.security.CurrentUser;
import am.itspace.cinemamodularweb.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WatchFilmPageController {

    private final FilmService filmService;
    private final FilmResponseMapper filmResponseMapper;

    @GetMapping("/watch/film")
    public String singleFilmPage(@AuthenticationPrincipal CurrentUser currentUser,
                                 ModelMap modelMap, @RequestParam("film_id") int film_id) {

        if (filmService.getFilmById(film_id) == null) {
            return "redirect:/";
        } else {
            modelMap.addAttribute("film", filmResponseMapper.map(filmService.getFilmById(film_id)));
        }
        if (currentUser != null) {
            modelMap.addAttribute("user", currentUser);
        }
        modelMap.addAttribute("comments", filmService.getFilmComments(film_id));
        return "main/watchFilmPage";
    }

    @GetMapping("/films")
    public String allFilmsPage() {
        return "main/allFilmsPage";
    }

    @GetMapping("/send/comment")
    public String sendComment(@RequestParam("text") String text,
                              @AuthenticationPrincipal CurrentUser currentUser,
                              @RequestParam("filmId") int film_id,
                              ModelMap modelMap) {

        Film filmId = filmService.saveComment(text, currentUser.getUser(), film_id);
        modelMap.addAttribute("film", filmId);
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("comments", filmService.getFilmComments(film_id));
        return "main/watchFilmPage";
    }

}
