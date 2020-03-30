package codesquad.service;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.FileManager;
import codesquad.domain.User;
import codesquad.dto.AttachmentDto;
import codesquad.dto.CommentDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import support.converter.AttachmentNameConverter;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final IssueService issueService;
    private final CommentService commentService;
    private final FileManager fileManager;

    public AttachmentService(AttachmentRepository attachmentRepository, IssueService issueService,
                             CommentService commentService, FileManager fileManager) {
        this.attachmentRepository = attachmentRepository;
        this.issueService = issueService;
        this.commentService = commentService;
        this.fileManager = fileManager;
    }

    @Transactional
    public void uploadFile(User loginUser, long issueId, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String manageName = fileManager.upload(file, AttachmentNameConverter.convertName(originalFileName));
        Attachment dbFile = new Attachment(originalFileName, manageName, loginUser, issueService.findById(issueId));

        attachmentRepository.save(dbFile);
        commentService.addComment(loginUser, issueId, new CommentDto(originalFileName));
    }

    @Transactional
    public FileSystemResource downloadFile(long id) {
        String manageName = findById(id).getManageName();
        return fileManager.download(manageName);
    }

    public List<AttachmentDto> findAllByIssue(Long issueId) {
        return attachmentRepository.findAllByIssueId(issueId)
                .stream()
                .map(Attachment::_toAttachmentDto)
                .collect(Collectors.toList());
    }

    public AttachmentDto findById(long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new)
                ._toAttachmentDto();
    }
}
