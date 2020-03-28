package codesquad.web.api;

import codesquad.domain.User;
import codesquad.dto.CommentDto;
import codesquad.security.LoginUser;
import codesquad.service.CommentService;
import codesquad.web.api.response.ApiResponse;
import codesquad.web.api.response.comment.CommentResponse;
import codesquad.web.api.response.comment.UpdateFormCommentResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
public class ApiCommentController {

    private final CommentService commentService;

    public ApiCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ApiResponse create(@LoginUser User loginUser, @PathVariable Long issueId, CommentDto commentDto) {
        CommentDto comment = commentService.addComment(loginUser, issueId, commentDto);
        CommentResponse data = new CommentResponse(
                comment.getId(), loginUser.getUserId(), comment.getContent(), comment.getCreatedDate());

        return new ApiResponse(true, data);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse delete(@LoginUser User loginUser, @PathVariable Long commentId) {
        commentService.delete(loginUser, commentId);

        return new ApiResponse(true, null);
    }

    @GetMapping("/{commentId}")
    public ApiResponse updateForm(@LoginUser User loginUser, @PathVariable Long commentId) {
        CommentDto comment = commentService.findById(loginUser, commentId)._toCommentDto();
        UpdateFormCommentResponse data = new UpdateFormCommentResponse(comment.getContent());

        return new ApiResponse(true, data);
    }

    @PutMapping("/{commentId}")
    public ApiResponse update(@LoginUser User loginUser, @PathVariable Long commentId, CommentDto commentDto) {
        CommentDto comment = commentService.update(loginUser, commentId, commentDto);
        CommentResponse data = new CommentResponse(
                comment.getId(), loginUser.getUserId(), comment.getContent(), comment.getCreatedDate());

        return new ApiResponse(true, data);
    }
}
