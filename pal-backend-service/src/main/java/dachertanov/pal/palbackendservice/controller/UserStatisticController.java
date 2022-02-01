package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackenddto.user.UserStatisticOutDto;
import dachertanov.pal.palbackendservice.service.UserStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private/statistic")
@AllArgsConstructor
public class UserStatisticController {
    private final UserStatisticService userStatisticService;

    @GetMapping("/get")
    public ResponseEntity<UserStatisticOutDto> getUserStatistic() {
        return userStatisticService.getUserStatistic()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
