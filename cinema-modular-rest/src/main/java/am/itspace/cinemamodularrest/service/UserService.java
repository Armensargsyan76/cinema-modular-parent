package am.itspace.cinemamodularrest.service;

import am.itspace.cinemamodularcommon.dto.userrequestdto.UserRequestDTO;
import am.itspace.cinemamodularcommon.dto.userrequestdto.UserUpdateRequestDTO;
import am.itspace.cinemamodularcommon.dto.userresponsedto.UserResponseDTO;
import am.itspace.cinemamodularcommon.entity.userdetail.Role;
import am.itspace.cinemamodularcommon.entity.userdetail.User;
import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularcommon.repository.UserRepository;
import am.itspace.cinemamodularrest.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CreatePictureUtil creatPicture;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO, MultipartFile multipartFile) {
        User user = userMapper.map(userRequestDTO);
        if (!multipartFile.isEmpty() && multipartFile.getSize() > 0) {
            user.setPictureUrl(creatPicture.creatPicture(multipartFile));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setEnable(true);
        user.setToken(null);
        user.setRegisteredDate(LocalDate.now());
        User save = userRepository.save(user);
        log.info("user registered {}", user.getEmail());
        return userMapper.map(save);
    }

    public boolean checkUniqueEmail(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        return byEmail.isPresent();
    }

    public UserResponseDTO update(int id, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userMapper.map(userUpdateRequestDTO);
        if (userUpdateRequestDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getPassword()));
        }
        user.setRole(Role.USER);
        user.setEnable(true);
        user.setId(id);
      return   userMapper.map(userRepository.save(user));


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

    public Page<User> getAllUsers(Pageable pageable) {
        if (userRepository.findAll(pageable).isEmpty()) {
            return null;
        }
        return userRepository.findAll(pageable);
    }


    public boolean existUserById(int id) {
      return userRepository.existsById(id);

    }


    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public User save(User user) {

        return userRepository.save(user);
    }

    public void deleteUserById(int id){
        userRepository.deleteById(id);

    }
}
