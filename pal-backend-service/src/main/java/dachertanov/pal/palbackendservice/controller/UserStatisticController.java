package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.user.UserStatisticOutDto;
import dachertanov.pal.palbackendservice.service.UserStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/statistic")
@AllArgsConstructor
public class UserStatisticController {
    private final UserStatisticService userStatisticService;

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<UserStatisticOutDto> getUserStatisticById(@PathVariable UUID userId) {
        return userStatisticService.getUserStatistic(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
