package am.itspace.cinemamodularweb.service;

import am.itspace.cinemamodularcommon.dto.userrequestdto.UserRequestDTO;
import am.itspace.cinemamodularcommon.dto.userrequestdto.UserUpdateRequestDTO;
import am.itspace.cinemamodularcommon.dto.userresponsedto.UserResponseDTO;
import am.itspace.cinemamodularcommon.entity.userdetail.Role;
import am.itspace.cinemamodularcommon.entity.userdetail.User;
import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularcommon.repository.UserRepository;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CreatePictureUtil creatPicture;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final FilmService filmService;

    public void registerUser(UserRequestDTO userRegisterRequestDTO, MultipartFile multipartFile) {
        User user = userMapper.map(userRegisterRequestDTO);
        if (!multipartFile.isEmpty() && multipartFile.getSize() > 0) {
            user.setPictureUrl(creatPicture.creatPicture(multipartFile));
        }
        userRepository.save(userBuild(user));
        sendEmail(user);
        log.info("user registered {}", user.getEmail());
    }

    public boolean verifyUser(String email, String token) {
        Optional<User> userOptional = userRepository.findByEmailAndToken(email, token);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        if (user.isEnable()) {
            return false;
        }
        user.setEnable(true);
        user.setToken(null);
        userRepository.save(user);
        log.info("user is verified{}", email);
        return true;
    }

    public boolean checkUniqueEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }

    public void update(int id, UserUpdateRequestDTO userUpdateRequestDTO) {
        User map = userMapper.map(userUpdateRequestDTO);
        if (userUpdateRequestDTO.getPassword() != null) {
            map.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getPassword()));
        }
        map.setRole(Role.USER);
        map.setEnable(true);
        map.setId(id);
        userMapper.map(userRepository.save(map));
    }

    public Page<User> getAllUsers(Pageable pageable) {
        if (userRepository.findAll(pageable).isEmpty()) {
            return null;
        }
        return userRepository.findAll(pageable);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public int getCountAllUsers() {
        return userRepository.countAllUsers();
    }

    public List<User> getLastFiveUsers() {
        if (userRepository.findLastFiveUsers().isEmpty()) {
            return null;
        }
        return userRepository.findLastFiveUsers();
    }

    public boolean deleteUserById(int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean sendEmail(User user) {
        try {
            mailService.sendHtmlEmail(user.getEmail(), "Please verify your email",
                    "Hi " + user.getName() + "\n" +
                            "Please verify your account by clicking on this link " +
                            "<a href=\"http://localhost:8080/user/verify?email=" + user.getEmail() + "&token=" + user.getToken() + "\">Activate</a>");

        } catch (MessagingException e) {
            log.error("error verify user by email");
            return false;
        }
        return true;
    }

    public ModelMap addAttributeForAdminPage(ModelMap modelMap, UserResponseDTO userResponse) {
        modelMap.addAttribute("user", userResponse);
        modelMap.addAttribute("countFilms", filmService.getCountAllFilms());
        modelMap.addAttribute("countUsers", getCountAllUsers());
        modelMap.addAttribute("topFiveFilmsByRating", filmService.getFiveFilmsByRating());
        modelMap.addAttribute("lastFiveUsers", getLastFiveUsers());
        return modelMap;
    }

    private User userBuild(User user) {
        user = User.builder()
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .isEnable(false)
                .registeredDate(LocalDate.now())
                .token(UUID.randomUUID().toString())
                .build();
        return user;
    }

}
