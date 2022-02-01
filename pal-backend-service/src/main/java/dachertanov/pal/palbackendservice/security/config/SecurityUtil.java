package dachertanov.pal.palbackendservice.security.config;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtil {
    public static UUID getCurrentUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UUID.fromString(userDetails.getUserId());
    }
}
