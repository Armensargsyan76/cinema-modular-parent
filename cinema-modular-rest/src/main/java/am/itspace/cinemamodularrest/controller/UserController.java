package am.itspace.cinemamodularrest.controller;

import am.itspace.cinemamodularcommon.dto.UserAuthDto;
import am.itspace.cinemamodularcommon.dto.UserAuthResponseDto;
import am.itspace.cinemamodularcommon.dto.userrequestdto.UserRequestDTO;
import am.itspace.cinemamodularcommon.dto.userrequestdto.UserUpdateRequestDTO;
import am.itspace.cinemamodularcommon.dto.userresponsedto.UserResponseDTO;
import am.itspace.cinemamodularcommon.entity.filmdetail.Comment;
import am.itspace.cinemamodularcommon.entity.userdetail.User;
import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularrest.security.CurrentUser;
import am.itspace.cinemamodularrest.service.CommentService;
import am.itspace.cinemamodularrest.service.UserService;
import am.itspace.cinemamodularrest.util.CreatePictureUtil;
import am.itspace.cinemamodularrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final CommentService commentService;
    private final CreatePictureUtil createPictureUtil;


    @PostMapping( "/users")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO, @RequestPart(value = "imageUser",required = false) MultipartFile multipartFile) {
        Optional<User> existingUser = userService.findByEmail(userRequestDTO.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return new ResponseEntity<>(userService.registerUser(userRequestDTO,multipartFile),HttpStatus.CREATED);
    }

    @PostMapping("/users/auth")
    public ResponseEntity<?> auth(@RequestBody UserAuthDto userAuthDto) {
        Optional<User> byEmail = userService.findByEmail(userAuthDto.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(UserAuthResponseDto.builder()
                        .token(jwtTokenUtil.generateToken(user.getEmail()))
                        .user(userMapper.map(user))
                        .build());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/user/comments")
    public ResponseEntity<List<Comment>> userComments(@AuthenticationPrincipal CurrentUser currentUser) {
        var commentByUserId = commentService.getCommentByUserId(currentUser.getUser().getId());
        if (!commentByUserId.isEmpty()) {
            return ResponseEntity.ok(commentByUserId);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users")
    ResponseEntity<UserResponseDTO> updateUser(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        UserResponseDTO updateUser = userService.update(currentUser.getUser().getId(), userUpdateRequestDTO);
        if (updateUser.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(updateUser);
    }


    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {
        if (userService.existUserById(id)) {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        }
    return ResponseEntity.notFound().build();
    }
    @GetMapping("/getImage")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String fileName) {
        if (createPictureUtil.getImage(fileName) == null){
            return null;
        }
        return createPictureUtil.getImage(fileName);
    }

}