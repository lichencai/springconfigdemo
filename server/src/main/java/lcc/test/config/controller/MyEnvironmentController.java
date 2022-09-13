package lcc.test.config.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author lichencai
 * @date 2022/9/13 9:24
 */
@Slf4j
@RestController
public class MyEnvironmentController {

    private String baseUrl = "http://localhost:6000/env/";

    @GetMapping("/getFile")
    public ResponseEntity<FileSystemResource> download(@RequestParam String filePath) throws UnsupportedEncodingException {

        String localFilePach = URLDecoder.decode(filePath, "UTF-8");

        String contentDisposition = ContentDisposition
                .builder("attachment")
                .filename(localFilePach)
                .build().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(localFilePach));
    }


}
