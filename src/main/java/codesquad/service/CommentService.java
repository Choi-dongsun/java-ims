package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Comment;
import codesquad.domain.CommentRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.CommentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final IssueService issueService;
    private final CommentRepository commentRepository;

    public CommentService(IssueService issueService, CommentRepository commentRepository) {
        this.issueService = issueService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentDto addComment(User loginUser, Long issueId, CommentDto commentDto) {
        Issue issue = issueService.findById(issueId);
        commentDto.setWriter(loginUser._toUserDto());
        commentDto.setIssue(issue._toIssueDto());
        Comment savedComment = commentRepository.save(commentDto._toComment());

        return savedComment._toCommentDto();
    }

    @Transactional
    public void addAttributeComment(User loginUser, Long issueId, String name) {
        addComment(loginUser, issueId, new CommentDto(name));
    }

    @Transactional
    public void delete(User loginUser, Long commentId) {
        Comment comment = findById(loginUser, commentId);
        comment.delete();
    }

    @Transactional
    public CommentDto update(User loginUser, Long commentId, CommentDto commentDto) {
        Comment comment = findById(loginUser, commentId);
        return comment.update(commentDto.getContent())._toCommentDto();

    }

    public List<CommentDto> findAllByIssue(Long issueId) {
        return commentRepository.findAllByIssueIdAndDeletedFalse(issueId)
                .stream()
                .map(Comment::_toCommentDto)
                .collect(Collectors.toList());
    }

    public Comment findById(User loginUser, Long commentId) {
        return commentRepository.findById(commentId)
                .filter(comment -> !comment.isDeleted())
                .filter(comment -> comment.isWriter(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }
}
