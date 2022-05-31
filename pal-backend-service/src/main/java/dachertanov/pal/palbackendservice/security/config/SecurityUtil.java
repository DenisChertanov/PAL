package dachertanov.pal.palbackendservice.security.config;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtil {
    public static boolean rootMode = false;
    public static UUID rootId;

    public static UUID getCurrentUserId() {
        if (rootMode) {
            return rootId;
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UUID.fromString(userDetails.getUserId());
    }
}
