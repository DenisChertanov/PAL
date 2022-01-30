package dachertanov.pal.palbackendservice.s3;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @SneakyThrows
    @Bean
    public MinioClient s3Client(S3Properties s3Properties) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(s3Properties.getUrl())
                .credentials(s3Properties.getAccessKey(), s3Properties.getSecretKey())
                .build();

        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(s3Properties.getBucket())
                .build());

        if (!exists) {
            throw new MinioException("Minio Bucket doesn't exist: " + s3Properties.getBucket());
        }

        return minioClient;
    }
}
