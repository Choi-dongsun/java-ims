package codesquad.web;

import codesquad.service.AttachmentService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileSystemResource> download(@PathVariable long id) throws Exception {
        FileSystemResource resource = attachmentService.downloadFile(id);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.setContentLength(resource.contentLength());
        header.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", resource.getFilename().substring(36)));

        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

}
