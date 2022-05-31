package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackendservice.s3.S3Service;
import dachertanov.pal.palbackendservice.security.config.JwtUtils;
import dachertanov.pal.palbackendservice.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {
    private final S3Service s3Service;
    private final JwtUtils jwtUtils;
    private final TestService testService;

    @PostMapping(value = "/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestPart @NotNull MultipartFile file,
                             @RequestPart @NotNull String imageKey) throws Exception {
        return s3Service.uploadImage(file, imageKey);
    }

    @GetMapping("/get-jwt/{id}")
    public String getJwt(@PathVariable UUID id) {
        return jwtUtils.generateToken(id.toString());
    }

    @GetMapping("/initialize-all-users")
    public ResponseEntity<String> initializeAllUsers() {
        testService.initializeAllUsers();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/create-playlist", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createUserTestPlaylist(@RequestPart @NotNull MultipartFile image,
                                                         @RequestPart @NotNull String userId) throws Exception {
        testService.createUserTestPlaylist(UUID.fromString(userId), image);
        return ResponseEntity.ok().build();
    }
}
