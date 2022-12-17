package am.itspace.cinemamodularweb.controller.filmdetailcontroller;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.ActorRequestDTO;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.ActorRequestMapper;
import am.itspace.cinemamodularweb.service.ActorService;
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
public class ActorController {

    private final CheckImportedData checkImportedData;
    private final ActorService actorService;
    private final ActorRequestMapper actorRequestMapper;

    @PostMapping("/add/actor")
    public String addActor(@ModelAttribute @Valid ActorRequestDTO actorRequestDTO, BindingResult bindingResult,
                           @RequestParam("imageActor") MultipartFile multipartFile, ModelMap modelMap) {

        if (checkImportedData.checkData(bindingResult, multipartFile, modelMap).isPresent()) {
            checkImportedData.checkData(bindingResult, multipartFile, modelMap).get();
            return "admin/addActor";
        }
        actorService.addActor(actorRequestMapper.map(actorRequestDTO), multipartFile);
        return "admin/addActor";
    }


}
