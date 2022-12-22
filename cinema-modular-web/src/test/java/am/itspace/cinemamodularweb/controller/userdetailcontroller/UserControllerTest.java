package am.itspace.cinemamodularweb.controller.userdetailcontroller;

import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularweb.service.UserService;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CreatePictureUtil createPictureUtil;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userResponseMapper;

    @MockBean
    private ModelMap modelMap;


    Map<String, Object> userRequestDTO = new HashMap<>() {{
        put("name", "some-name");
        put("surname", "some-surname");
        put("email", "email@mail.com");
        put("password", "some-password");
        put("pictureUrl", "picture");
        put("isEnable", true);
    }};

//    @Test
//    public void registerUser() throws Exception {
//        FileInputStream fis = new FileInputStream("C:\\Users\\Tornado\\IdeaProjects\\cinema-modular-parent\\cinema-modular-web\\src\\test\\resources\\lind hemilton.jpg");
//        MockMultipartFile image = new MockMultipartFile("image", fis);
//
//        HashMap<String, String> contentTypeParams = new HashMap<>();
//        contentTypeParams.put("boundary", "265001916915724");
//        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
//
//        when(createPictureUtil.isPictureNotAllowedType(any())).thenReturn(false);
//        when(modelMap.addAttribute("errorMessageFile", "Please choose only image")).thenReturn(modelMap);
//        doNothing().when(userService).registerUser(any(), any());
//
//        mvc.perform(
//                MockMvcRequestBuilders.post("/register/user")
//                        .contentType(mediaType)
//                        .content(new ObjectMapper().findAndRegisterModules().writeValueAsString(userRequestDTO))
//                        .param("pictureUrl", String.valueOf(image))
//        ).andExpect(status().isOk());
//    }

}