package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.user.UserInfoOutDto;
import dachertanov.pal.palbackenddto.user.UserSearchInDto;
import dachertanov.pal.palbackenddto.user.UserSearchOutDto;
import dachertanov.pal.palbackenddto.user.rating.UserRatingListOutDto;
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

    @GetMapping("/get-by-id/{username}")
    public ResponseEntity<UserInfoOutDto> getUserById(@PathVariable String username) {
        return userService.getUserByUsername(username)
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

    @GetMapping("/me")
    public ResponseEntity<UserInfoOutDto> getCurrentUserInfo() {
        return ResponseEntity.ok(userService.getCurrentUserInfoOutDto());
    }

    @GetMapping("/activity-rating")
    public ResponseEntity<UserRatingListOutDto> getUserActivityRating() {
        return ResponseEntity.ok(userService.getUserActivityRating());
    }
}
