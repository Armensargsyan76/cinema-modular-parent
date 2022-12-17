package am.itspace.cinemamodularweb;

import am.itspace.cinemamodularcommon.mapper.userrequestmapper.UserMapper;
import am.itspace.cinemamodularweb.service.UserService;
import am.itspace.cinemamodularweb.util.CreatePictureUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.Map;

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

    ModelMap modelMaps = new ModelMap();

//    @Test
//    public void registerUser() throws Exception {
//        when(createPictureUtil.isPictureNotAllowedType(any())).thenReturn(false);
//        when(modelMap.addAttribute("errorMessageFile", "Please choose only image")).thenReturn(modelMap);
//        doNothing().when(userService).registerUser(any(), any());
//
//        mvc.perform(
//                MockMvcRequestBuilders.post("/register/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().findAndRegisterModules().writeValueAsString(userRequestDTO))
//                        .param("pictureUrl", "picture")
//        ).andExpect(status().isOk());
//    }
}