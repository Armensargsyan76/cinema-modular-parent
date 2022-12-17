package am.itspace.cinemamodularweb.controller.cinemadetailcontroller;

import am.itspace.cinemamodularcommon.dto.cinemarequestdto.BoxOfficeRequestDTO;
import am.itspace.cinemamodularcommon.dto.cinemarequestdto.CinemaRequestDTO;
import am.itspace.cinemamodularcommon.mapper.cinemarequestmapper.CinemaRequestMapper;
import am.itspace.cinemamodularweb.security.CurrentUser;
import am.itspace.cinemamodularweb.service.BoxOfficeService;
import am.itspace.cinemamodularweb.service.CinemaService;
import am.itspace.cinemamodularweb.util.CheckImportedData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class CinemaController {

    private final CheckImportedData checkImportedData;
    private final CinemaService cinemaService;
    private final CinemaRequestMapper cinemaRequestMapper;
    private final BoxOfficeService boxOfficeService;

    @PostMapping("/add/cinema")
    public String addCinema(@ModelAttribute @Valid CinemaRequestDTO cinemaRequestDTO, BindingResult bindingResult,
                            @RequestParam("imageCinema") MultipartFile multipartFile, ModelMap modelMap) {

        if (checkImportedData.checkDataAndEmail(bindingResult, cinemaRequestDTO.getEmail(), multipartFile, modelMap).isPresent()) {
            checkImportedData.checkDataAndEmail(bindingResult, cinemaRequestDTO.getEmail(), multipartFile, modelMap);
            return "admin/addCinema";
        }
        cinemaService.addCinema(cinemaRequestMapper.map(cinemaRequestDTO), multipartFile);
        return "admin/addCinema";
    }

    @PostMapping("/add/films-in/cinema")
    public String addFilmsInCinema(@RequestParam("cinemaId") int cinemaId,
                                   @RequestParam("filmsId") List<Integer> filmsId) {

        cinemaService.addFilmsInCinema(cinemaId, filmsId);
        return "admin/addFilmInCinema";
    }

    @PostMapping("/buy/ticket/{filmId}/{cinemaId}")
    public String buyTicket(@PathVariable("filmId") int filmId, @PathVariable("cinemaId") int cinemaId,
                            @ModelAttribute @Valid BoxOfficeRequestDTO boxOfficeRequestDTO,
                            @AuthenticationPrincipal CurrentUser currentUser) {

        if (boxOfficeService.createBoxOffice(boxOfficeRequestDTO, filmId, cinemaId, currentUser.getUser().getId())) {
            boxOfficeService.createBoxOffice(boxOfficeRequestDTO, filmId, cinemaId, currentUser.getUser().getId());
            return "redirect:/cinema/page/" + cinemaId;
        }
        return "redirect:/get/choose/film/" + filmId + "/" + cinemaId;
    }

}
