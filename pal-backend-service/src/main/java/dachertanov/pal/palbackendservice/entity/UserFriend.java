package dachertanov.pal.palbackendservice.entity;

import dachertanov.pal.palbackendservice.entity.id.UserFriendId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.UUID;

@Entity
@Data
@IdClass(UserFriendId.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserFriend {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "friend_id")
    private UUID friendId;
}
