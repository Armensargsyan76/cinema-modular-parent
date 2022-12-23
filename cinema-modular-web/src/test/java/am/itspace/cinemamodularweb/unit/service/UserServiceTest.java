package am.itspace.cinemamodularweb.unit.service;

import am.itspace.cinemamodularcommon.dto.userrequestdto.UserRequestDTO;
import am.itspace.cinemamodularcommon.dto.userrequestdto.UserUpdateRequestDTO;
import am.itspace.cinemamodularcommon.dto.userresponsedto.UserResponseDTO;
import am.itspace.cinemamodularcommon.entity.userdetail.Role;
import am.itspace.cinemamodularcommon.entity.userdetail.User;
import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularcommon.repository.UserRepository;
import am.itspace.cinemamodularweb.service.MailService;
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
    private UserMapper userMapper;
    @Mock
    private CreatePictureUtil createPictureUtil;
    @Mock
    private MultipartFile multipartFile;
    @Mock
    private Pageable pageable;
    @Mock
    private User userMock;
    @Mock
    private MailService mailService;
    @InjectMocks
    private UserService userService;

    private final UserResponseDTO userResponseDTO = UserResponseDTO.builder()
            .id(1)
            .name("some-name")
            .surname("some-surname")
            .email("some@gmailcom")
            .films(null)
            .pictureUrl("some-picture-name")
            .role(Role.USER)
            .registeredDate(null)
            .build();

    private final UserRequestDTO userRequestDTO = UserRequestDTO.builder()
            .name("some-name")
            .surname("some-surname")
            .email("some@gmailcom")
            .password("some-password")
            .pictureUrl("some-picture-name")
            .build();
    private final UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder()
            .name("some-name")
            .surname("some-surname")
            .email("some@gmailcom")
            .password("some-password")
            .build();

    private final User userTest = User.builder()
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

    private final List<User> users = new ArrayList<>();

    @Test
    @DisplayName("successfully save user")
    void registerUser() {
        String pictureName = "some-picture-name";
        String password = "some-password";
        lenient().when(userMapper.map(userRequestDTO)).thenReturn(userTest);
        when(createPictureUtil.creatPicture(any())).thenReturn(pictureName);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getSize()).thenReturn(2L);
        lenient().when(passwordEncoder.encode(password)).thenReturn(password);
        lenient().when(userRepository.save(userTest)).thenReturn(userTest);
        userService.registerUser(userRequestDTO, multipartFile);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("successfully check unique email")
    void checkUniqueEmail() {
        String email = "some email";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        boolean ok = userService.checkUniqueEmail(email);
        assertFalse(ok);
    }

    @Test
    @DisplayName("failed check unique email")
    void checkUniqueFailEmail() {
        String email = "some email";
        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(userTest));
        boolean fail = userService.checkUniqueEmail(email);
        assertTrue(fail);
    }

    @Test
    @DisplayName("successfully get users")
    void getAllUsersPageTest() {
        users.add(userTest);
        Page<User> page = new PageImpl<>(users);
        when(userRepository.findAll(pageable)).thenReturn(page);
        assertEquals(userRepository.findAll(pageable), page);
    }

    @Test
    @DisplayName("successfully find user by id")
    void getUserByIdTest() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userTest));
        assertEquals(userRepository.findById(1).get(), userTest);
    }

    @Test
    @DisplayName("failed get user by id")
    void getUserByIdFailTest() {
        when(userRepository.findById(0)).thenReturn(Optional.empty());
        Optional<User> userById = userRepository.findById(0);
        assertTrue(userById.isEmpty());
    }

    @Test
    @DisplayName("successfully verify user by email and token")
    void verifyUserTest() {
        String email = "some@gmailcom";
        String token = "some-token";
        userTest.setEnable(false);
        when(userRepository.findByEmailAndToken(email, token)).thenReturn(Optional.of(userTest));
        when(userRepository.save(any())).thenReturn(userTest);
        userService.verifyUser(email, token);
        verify(userRepository, times(1)).save(userTest);
    }

    @Test
    @DisplayName("failed verify user by email and token")
    void verifyUserFailNotFoundTest() {
        String email = "some@gmailcom";
        String token = "some-token";
        userTest.setEnable(false);
        when(userRepository.findByEmailAndToken(email, token)).thenReturn(Optional.empty());
        assertFalse(userService.verifyUser(email, token));
    }

    @Test
    @DisplayName("successfully verify user by email and token")
    void verifyUserEnableTest() {
        String email = "some@gmailcom";
        String token = "some-token";
        userTest.setEnable(true);
        when(userRepository.findByEmailAndToken(email, token)).thenReturn(Optional.of(userTest));
        assertFalse(userService.verifyUser(email, token));
    }

    @Test
    @DisplayName("successfully update user")
    void updateUser() {
        when(userMapper.map(userUpdateRequestDTO)).thenReturn(userTest);
        when(userMapper.map(userTest)).thenReturn(userResponseDTO);
        when(userRepository.save(userTest)).thenReturn(userTest);
        userService.updateUser(1, userUpdateRequestDTO);
        verify(userRepository, times(1)).save(userTest);
    }

    @Test
    @DisplayName("successfully get count users")
    void getCountAllUsersTest() {
        int count = 10;
        when(userService.getCountAllUsers()).thenReturn(10);
        assertEquals(userService.getCountAllUsers(), count);
    }

    @Test
    @DisplayName("successfully delete user by id")
    void deleteFilmTrueTest() {
        boolean isDelete = true;
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(userTest));
        doNothing().when(userRepository).deleteById(id);
        boolean ok = userService.deleteUserById(id);
        assertEquals(isDelete, ok);
    }

}

