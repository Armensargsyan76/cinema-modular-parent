package am.itspace.cinemamodularweb.unit.service;

import am.itspace.cinemamodularcommon.dto.userrequestdto.UserRequestDTO;
import am.itspace.cinemamodularcommon.entity.userdetail.Role;
import am.itspace.cinemamodularcommon.entity.userdetail.User;
import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularcommon.repository.UserRepository;
import am.itspace.cinemamodularweb.service.UserService;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userRequestMapper;
    @Mock
    private CreatePictureUtil createPictureUtil;
    @Mock
    private MultipartFile multipartFile;
    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequestDTO = UserRequestDTO.builder()
            .name("some-name")
            .surname("some-surname")
            .email("some@gmailcom")
            .password("some-password")
            .pictureUrl("some-picture-name")
            .build();

    private User user = User.builder()
            .id(1)
            .name("some-name")
            .surname("some-surname")
            .email("some@gmailcom")
            .password("some-password")
            .role(Role.USER)
            .pictureUrl("some-picture-name")
            .isEnable(true)
            .token("some-token")
            .build();


    @Test
    @DisplayName("successfully save user")
    void registerUser() {
        String pictureName = "some-picture-name";
        String password = "some-password";

        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(userRepository.save(user)).thenReturn(user);

        userService.registerUser(userRequestDTO, multipartFile);
    }


}

