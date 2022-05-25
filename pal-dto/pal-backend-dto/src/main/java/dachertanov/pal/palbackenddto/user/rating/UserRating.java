package dachertanov.pal.palbackenddto.user.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRating {
    private UUID userId;
    private String imageUrl;
    private String firstName;
    private String lastName;
    private String username;
    private Long count;
    private Long order;

    public UserRating(UUID userId, String imageUrl, String firstName, String lastName, String username, Long count) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.count = count;
    }
}
