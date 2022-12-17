package am.itspace.cinemamodularweb.controller.filmdetailcontroller;


import am.itspace.cinemamodularcommon.dto.filmrequestdto.DirectorRequestDTO;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.DirectorRequestMapper;
import am.itspace.cinemamodularweb.service.DirectorService;
import am.itspace.cinemamodularweb.util.CheckImportedData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class DirectorController {

    private final CheckImportedData checkImportedData;
    private final DirectorService directorService;
    private final DirectorRequestMapper directorRequestMapper;

    @PostMapping("/add/director")
    public String addDirector(@ModelAttribute @Valid DirectorRequestDTO directorRequestDTO, BindingResult bindingResult,
                              @RequestParam("imageDirector") MultipartFile multipartFile, ModelMap modelMap) {

        if (checkImportedData.checkData(bindingResult, multipartFile, modelMap).isPresent()) {
            checkImportedData.checkData(bindingResult, multipartFile, modelMap).get();
            return "admin/addDirector";
        }
        directorService.addDirector(directorRequestMapper.map(directorRequestDTO), multipartFile);
        return "admin/addDirector";
    }

}
