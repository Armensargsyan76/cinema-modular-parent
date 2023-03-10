package am.itspace.cinemamodularweb.controller.filmdetailcontroller;

import am.itspace.cinemamodularcommon.entity.filmdetail.Film;
import am.itspace.cinemamodularcommon.entity.userdetail.FavoriteMovies;
import am.itspace.cinemamodularcommon.entity.userdetail.UserFilm;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.FilmResponseMapper;
import am.itspace.cinemamodularweb.security.CurrentUser;
import am.itspace.cinemamodularweb.service.CommentService;
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
    private final CommentService commentService;

    @GetMapping("/watch/film")
    public String singleFilmPage(@AuthenticationPrincipal CurrentUser currentUser,
                                 ModelMap modelMap,
                                 @RequestParam("film_id") int film_id) {
        if (filmService.getFilmById(film_id) == null) {
            return "redirect:/";
        }
        modelMap.addAttribute("film", filmResponseMapper.map(filmService.getFilmById(film_id)));
        if (currentUser != null) {
            modelMap.addAttribute("haveRating", filmService.getUserFilmByFilmIdAndUserId(film_id, currentUser.getUser().getId()));
            modelMap.addAttribute("user", currentUser);
            modelMap.addAttribute("haveFavoriteFilm", filmService.getFavoriteFilm(film_id, currentUser.getUser().getId()));
        }
        modelMap.addAttribute("comments", commentService.getFilmComments(film_id));
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
                              ModelMap modelMap
    ) {
        if (filmService.getFilmById(film_id) == null) {
            return "redirect:/";
        }
        Film film = filmService.saveComment(text, currentUser.getUser(), film_id);
        modelMap.addAttribute("film", film);
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("haveRating", filmService.getUserFilmByFilmIdAndUserId(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("comments", commentService.getFilmComments(film_id));
        modelMap.addAttribute("haveFavoriteFilm", filmService.getFavoriteFilm(film_id, currentUser.getUser().getId()));
        return "main/watchFilmPage";
    }

    @GetMapping("/send/rating")
    public String sendRating(@RequestParam("rate") int rate,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             @RequestParam("film_id") int film_id,
                             ModelMap modelMap
    ) {
        if (filmService.getFilmById(film_id) == null || rate > 10 || rate < 0) {
            return "redirect:/";
        }
        filmService.starsRating(rate, currentUser.getUser(), film_id);
        modelMap.addAttribute("film", filmResponseMapper.map(filmService.getFilmById(film_id)));
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("haveFavoriteFilm", filmService.getFavoriteFilm(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("haveRating", filmService.getUserFilmByFilmIdAndUserId(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("comments", commentService.getFilmComments(film_id));

        return "main/watchFilmPage";
    }

    @GetMapping("/drop/rating")
    public String dropRating(@RequestParam(name = "film_id") int film_id,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             ModelMap modelMap) {

        if (filmService.getFilmById(film_id) == null) {
            return "redirect:/";
        }

        modelMap.addAttribute("film", filmResponseMapper.map(filmService.getFilmById(film_id)));
        UserFilm userFilm = filmService.getUserFilmByFilmIdAndUserId(film_id, currentUser.getUser().getId());
        filmService.dropRating(userFilm.getId());
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("haveFavoriteFilm", filmService.getFavoriteFilm(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("haveRating", filmService.getUserFilmByFilmIdAndUserId(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("comments", commentService.getFilmComments(film_id));
        modelMap.addAttribute("haveFavoriteFilm", filmService.getFavoriteFilm(film_id, currentUser.getUser().getId()));

        return "main/watchFilmPage";
    }

    @GetMapping("/favorite/movies")
    public String favoriteMovies(@RequestParam(name = "film_id") int film_id,
                                 @AuthenticationPrincipal CurrentUser currentUser,
                                 ModelMap modelMap) {
        if (filmService.getFilmById(film_id) == null) {
            return "redirect:/";
        }
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("film", filmResponseMapper.map(filmService.getFilmById(film_id)));
        filmService.saveFavoriteMovies(film_id, currentUser.getUser());
        modelMap.addAttribute("haveFavoriteFilm", filmService.getFavoriteFilm(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("haveRating", filmService.getUserFilmByFilmIdAndUserId(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("comments", commentService.getFilmComments(film_id));
        return "main/watchFilmPage";
    }
    @GetMapping("/delete/favorite/film")
    public String deleteFavoriteFilm(@RequestParam(name = "film_id") int film_id,
                                       @AuthenticationPrincipal CurrentUser currentUser,
                                       ModelMap modelMap){
        if (filmService.getFilmById(film_id) == null) {
            return "redirect:/";
        }
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("film", filmResponseMapper.map(filmService.getFilmById(film_id)));
        FavoriteMovies favoriteFilm = filmService.getFavoriteFilm(film_id, currentUser.getUser().getId());
        filmService.deleteFavoriteFilmById(favoriteFilm.getId());
        modelMap.addAttribute("haveFavoriteFilm", filmService.getFavoriteFilm(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("haveRating", filmService.getUserFilmByFilmIdAndUserId(film_id, currentUser.getUser().getId()));
        modelMap.addAttribute("comments", commentService.getFilmComments(film_id));
        return "main/watchFilmPage";
    }
}
