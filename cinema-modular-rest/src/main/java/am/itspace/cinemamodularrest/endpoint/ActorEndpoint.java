package am.itspace.cinemamodularrest.endpoint;

import am.itspace.cinemamodularcommon.dto.filmrequestdto.ActorRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmrequestdto.ActorUpdateRequestDTO;
import am.itspace.cinemamodularcommon.dto.filmresponsedto.ActorResponseDTO;
import am.itspace.cinemamodularcommon.dto.filmresponsedto.FilmResponseDTO;
import am.itspace.cinemamodularcommon.mapper.filmrequestmapper.ActorRequestMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.ActorResponseMapper;
import am.itspace.cinemamodularcommon.mapper.filmresponsemapper.FilmResponseMapper;
import am.itspace.cinemamodularrest.exception.EntityNotFoundException;
import am.itspace.cinemamodularrest.service.ActorService;
import am.itspace.cinemamodularrest.util.CreatePictureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorEndpoint {

    private final ActorService actorService;
    private final ActorRequestMapper actorRequestMapper;
    private final ActorResponseMapper actorResponseMapper;
    private final CreatePictureUtil createPictureUtil;
    private final FilmResponseMapper filmResponseMapper;

    @PostMapping
    public ResponseEntity<ActorResponseDTO> addActor(@RequestBody @Valid ActorRequestDTO actorRequestDTO,
                                                     @RequestParam(name = "actorImage") MultipartFile multipartFile) {
        actorService.addActor(actorRequestMapper.map(actorRequestDTO), multipartFile);
        return ResponseEntity.ok(actorResponseMapper.map(actorRequestMapper.map(actorRequestDTO)));
    }

    @GetMapping
    public ResponseEntity<List<ActorResponseDTO>> getAllActors() throws EntityNotFoundException {
        return ResponseEntity.ok(actorResponseMapper.map(actorService.findAllActors()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorResponseDTO> getActorById(@PathVariable("id") int id) throws EntityNotFoundException {
        return ResponseEntity.ok(actorResponseMapper.map(actorService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActorById(@PathVariable("id") int id) throws EntityNotFoundException {
        actorService.deleteActorById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorResponseDTO> updateActorById(@PathVariable("id") int id,
                                                            @RequestBody ActorUpdateRequestDTO actorUpdateRequestDTO) throws EntityNotFoundException {
        return ResponseEntity.ok(actorService.updateActor(id, actorUpdateRequestDTO));
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<List<FilmResponseDTO>> getFilmsByActor(@PathVariable("id") int id) {
        return ResponseEntity.ok(filmResponseMapper.map(actorService.getFilmByActor(id)));
    }

    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] getImage(@RequestParam("picName") String fileName) {
        if (createPictureUtil.getImage(fileName) == null) {
            return null;
        }
        return createPictureUtil.getImage(fileName);
    }

}
