package dachertanov.pal.palbackendservice.s3;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@AllArgsConstructor
@Service
public class S3Service {
    private final MinioClient minioClient;
    private final S3Properties s3Properties;

    /**
     * Загрузка файла по ключу в S3 (для проверки работы minio)
     */
    public String uploadImage(MultipartFile file, String imageKey) throws Exception {
        String filePath = getFile(file);
        String filePathInBucket = "images" + File.separator + imageKey + File.separator + file.getOriginalFilename();

        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(s3Properties.getBucket())
                        .object(filePathInBucket)
                        .filename(filePath)
                        .build());
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(s3Properties.getBucket())
                        .object(filePathInBucket)
                        .build());
        deleteTempFile(filePath);

        return url;
    }

    private String getFile(MultipartFile file) throws Exception {
        File putFile = new File("/tmp/" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(putFile);
        fos.write(file.getBytes());
        return putFile.getPath();
    }

    private void deleteTempFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }
}
