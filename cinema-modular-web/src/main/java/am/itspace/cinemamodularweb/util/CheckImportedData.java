package am.itspace.cinemamodularweb.util;

import am.itspace.cinemamodularweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CheckImportedData {

    private final CreatePictureUtil createPictureUtil;
    private final UserService userService;

    public Optional<ModelMap> checkData(BindingResult bindingResult, MultipartFile multipartFile, ModelMap modelMap) {
        if (bindingResult.hasErrors() || (createPictureUtil.isPictureNotAllowedType(multipartFile))) {
            if (bindingResult.hasErrors()) {
                checkBinding(bindingResult, modelMap);
            }
            if (createPictureUtil.isPictureNotAllowedType(multipartFile)) {
                modelMap.addAttribute("errorMessageFile", "Please choose only image");
            }
            return Optional.of(modelMap);
        }
        return Optional.empty();
    }

    public Optional<ModelMap> checkDataAndEmail(BindingResult bindingResult, String email,
                                            MultipartFile multipartFile, ModelMap modelMap) {
        if (bindingResult.hasErrors() || checkUniqueEmail(email)
                || createPictureUtil.isPictureNotAllowedType(multipartFile)) {
            if (bindingResult.hasErrors()) {
                checkBinding(bindingResult, modelMap);
            }
            if (checkUniqueEmail(email)) {
                modelMap.addAttribute("errorMessageEmail", "email already exist");
            }
            if (createPictureUtil.isPictureNotAllowedType(multipartFile)) {
                modelMap.addAttribute("errorMessageFile", "Please choose only image");
            }
            return Optional.of(modelMap);
        }
        return Optional.empty();
    }

    private ModelMap checkBinding(BindingResult bindingResult, ModelMap modelMap) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            String[] name = error.getDefaultMessage().split(" ");
            modelMap.addAttribute("errorMessage" + name[1], error.getDefaultMessage());
        }
        return modelMap;
    }

    private boolean checkUniqueEmail(String email) {
        return userService.checkUniqueEmail(email);
    }

}