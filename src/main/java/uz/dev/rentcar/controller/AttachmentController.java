package uz.dev.rentcar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.service.template.AttachmentService;

import java.nio.file.Path;

/**
 * Created by: asrorbek
 * DateTime: 7/21/25 16:16
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {

        Attachment attachment = attachmentService.downloadFile(id);

        String path = attachment.getPath();

        Resource resource = new FileSystemResource(Path.of(path));

        return ResponseEntity
                .status(200)
                .header("Content-Disposition", "attachment; filename=\"%s\"".formatted(attachment.getOriginalName()))
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .contentLength(attachment.getSize())
                .body(resource);
    }

}
