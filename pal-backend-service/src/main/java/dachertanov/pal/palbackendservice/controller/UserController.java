package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackenddto.user.UserSearchInDto;
import dachertanov.pal.palbackenddto.user.UserSearchOutDto;
import dachertanov.pal.palbackendservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<UserInfoOutDto> getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/search")
    public Page<UserSearchOutDto> findUsers(@RequestBody UserSearchInDto userSearchInDto) {
        return userService.findUsers(userSearchInDto);
    }

    @PostMapping(value = "/upload-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserInfoOutDto> uploadAnimeImage(@RequestPart @NotNull MultipartFile file) throws Exception {
        return ResponseEntity.ok(userService.uploadUserImage(file));
    }
}
