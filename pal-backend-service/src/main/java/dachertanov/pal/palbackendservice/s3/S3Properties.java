package dachertanov.pal.palbackendservice.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "s3")
public class S3Properties {
    private String connectionUrl;
    private String objectUrl;
    private String bucket;
    private String publicFolder;
    private String accessKey;
    private String secretKey;
}