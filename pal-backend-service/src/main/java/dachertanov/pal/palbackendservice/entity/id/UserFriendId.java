package dachertanov.pal.palbackendservice.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendId implements Serializable {
    private UUID userId;
    private UUID friendId;
}
