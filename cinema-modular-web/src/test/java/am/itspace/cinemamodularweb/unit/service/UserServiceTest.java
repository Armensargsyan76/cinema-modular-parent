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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @Mock
    private Pageable pageable;
    @InjectMocks
    private UserService userService;

    private final UserRequestDTO userRequestDTO = UserRequestDTO.builder()
            .name("some-name")
            .surname("some-surname")
            .email("some@gmailcom")
            .password("some-password")
            .pictureUrl("some-picture-name")
            .build();

    private final User user = User.builder()
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
        when(userRequestMapper.map(userRequestDTO)).thenReturn(user);
        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(userRepository.save(user)).thenReturn(user);
        userService.registerUser(userRequestDTO, multipartFile);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("successfully check unique email")
    void checkUniqueEmail(){
        String email = "some email";
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(null));
        boolean ok = userService.checkUniqueEmail(email);
        assertFalse(ok);
    }

    @Test
    @DisplayName("failed check unique email")
    void checkUniqueFailEmail(){
        String email = "some email";
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));
        boolean fail = userService.checkUniqueEmail(email);
        assertTrue(fail);
    }

    @Test
    @DisplayName("successfully get users")
    void getAllUsersPageTest() {
        List<User> users = new ArrayList<>();
        users.add(user);
        Page<User> page = new PageImpl<>(users);
        when(userRepository.findAll(pageable)).thenReturn(page);
        Page<User> all = userRepository.findAll(pageable);
        assertEquals(all, page);
    }

    @Test
    @DisplayName("successfully find user by id")
    void getUserByIdTest(){
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Optional<User> byId = userRepository.findById(1);
        assertEquals(byId.get(), user);
    }

    @Test
    @DisplayName("failed get user by id")
    void getUserByIdFailTest() {
        when(userRepository.findById(0)).thenReturn(Optional.ofNullable(null));
        Optional<User> userById = userRepository.findById(0);
        assertTrue(userById.isEmpty());
    }

}

