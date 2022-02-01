package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.user.UserFriendListOutDto;
import dachertanov.pal.palbackenddto.user.UserFriendOutDto;
import dachertanov.pal.palbackendservice.service.UserFriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/private/friend")
@AllArgsConstructor
public class UserFriendController {
    private final UserFriendService userFriendService;

    @GetMapping("/add/{friendId}")
    public ResponseEntity<UserFriendOutDto> addFriend(@PathVariable UUID friendId) {
        return userFriendService.addFriend(friendId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-all")
    public ResponseEntity<UserFriendListOutDto> getAllFriends() {
        return userFriendService.getAllFriends()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
