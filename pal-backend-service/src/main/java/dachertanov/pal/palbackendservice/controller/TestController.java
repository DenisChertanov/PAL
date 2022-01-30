package dachertanov.pal.palbackendservice.controller;

import dachertanov.pal.palbackendservice.s3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/test")
@AllArgsConstructor
public class TestController {
    private final S3Service s3Service;

    @PostMapping(value = "/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestPart @NotNull MultipartFile file,
                             @RequestPart @NotNull String imageKey) throws Exception {
        return s3Service.uploadImage(file, imageKey);
    }
}
