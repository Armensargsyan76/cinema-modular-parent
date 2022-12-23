package am.itspace.cinemamodularweb.controller.userdetailcontroller;

import am.itspace.cinemamodularcommon.dto.userrequestdto.UserRequestDTO;
import am.itspace.cinemamodularcommon.dto.userrequestdto.UserUpdateRequestDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Comment;
import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularweb.security.CurrentUser;
import am.itspace.cinemamodularweb.service.CommentService;
import am.itspace.cinemamodularweb.service.UserService;
import am.itspace.cinemamodularweb.util.CheckImportedData;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final CreatePictureUtil createPictureUtil;
    private final CheckImportedData checkImportedData;
    private final UserService userService;
    private final CommentService commentService;
    private final UserMapper userMapper;


    @GetMapping("/login")
    public String login() {
        return "main/login";
    }

    @GetMapping("/forgot/password")
    public String forgotPassword() {
        return "main/forgot";
    }

    @GetMapping("/user/login")
    public String LoginPage() {
        return "main/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "main/register";
    }

    @GetMapping("/getImage")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String fileName) {
        if (createPictureUtil.getImage(fileName) == null) {
            return null;
        }
        return createPictureUtil.getImage(fileName);
    }

    @GetMapping("/editUser")
    public String editUserPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap map) {
        map.addAttribute("user", userMapper.map(currentUser.getUser()));
        return "saveUser";
    }

    @PostMapping("/register/user")
    public String registerUser(@ModelAttribute @Valid UserRequestDTO userRequestDTO, BindingResult bindingResult,
                               @RequestParam("imageUser") MultipartFile multipartFile, ModelMap modelMap) {
        if (checkImportedData.checkDataAndEmail(bindingResult, userRequestDTO.getEmail(), multipartFile, modelMap).isPresent()) {
            checkImportedData.checkDataAndEmail(bindingResult, userRequestDTO.getEmail(), multipartFile, modelMap).get();
            return "main/register";
        }
        if (userService.sendEmail(userMapper.map(userRequestDTO))) {
            modelMap.addAttribute("errorMessageSendEmail", "send email failed");
        }
        userService.registerUser(userRequestDTO, multipartFile);
        return "redirect:/user/login";
    }

    @GetMapping("/user/verify")
    public String verifyUser(@RequestParam("email") String email, ModelMap modelMap,
                             @RequestParam("token") String token) {
        try {
            if (!userService.verifyUser(email, token)) {
                modelMap.addAttribute("errorMessageDoesntEmailOrAlreadyEnabled",
                        "User Does not exists with email and token or User already enabled");
                return "main/mainHome";
            }
            userService.verifyUser(email, token);
        } catch (Exception e) {
            modelMap.addAttribute("errorMessageVerify", "verify with email failed");
            return "main/mainHome";
        }
        return "redirect:/";
    }

    @PostMapping("/editUser/{id}")
    public String editUserPage(@PathVariable("id") int id, @ModelAttribute UserUpdateRequestDTO userUpdateRequestDTO) {
        userService.updateUser(id, userUpdateRequestDTO);
        return "redirect:/";
    }

    @GetMapping("/user/comments")
    public String userComments(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        List<Comment> commentByUserId = commentService.getCommentByUserId(currentUser.getUser().getId());
        modelMap.addAttribute("comments", commentByUserId);
        return "main/allComments";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id, ModelMap modelMap, Pageable pageable) {
        if (userService.deleteUserById(id)) {
            return "redirect:/users/all";
        }
        modelMap.addAttribute("countAllUsers", userService.getCountAllUsers());
        modelMap.addAttribute("allUsers", userService.getAllUsers(pageable));
        modelMap.addAttribute("idNotExist", "id not exist");
        return "admin/allUsers";
    }

}