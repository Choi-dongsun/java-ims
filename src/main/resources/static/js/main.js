$(document).on('click', '.mdl-menu__item', decideIssueAttribute);
$(document).on('click', '.comment-add button[type=submit]', addComment);
$(document).on('click', '.comment-delete button[type=submit]', deleteComment);
$(document).on('click', '.comment-edit button[type=submit]', editRequest);
$(document).on('click', '.comment-edit-form[type=submit]', editComment);

function decideIssueAttribute(e) {
    e.preventDefault();
    var url = $(this).find('a').attr('href');

    $.ajax({
        type: 'get',
        url: url,
        error: onError,
        success: function onSuccess(data, status) {
            alert("지정에 성공하였습니다!");
            var addTemplate = $("#commentTemplate").html();
            var template = addTemplate.format(data.response.userId, data.response.name,
                data.response.createDate, data.response.commentId);
            $(".comments").append(template);
        }
    });
}

function addComment(e) {
    e.preventDefault();
    var url = $(this).parent().attr('action');
    var comment = $(this).parent().serialize();

    $.ajax({
        type: 'post',
        url: url,
        data: comment,
        dataType: 'json',
        error: onError,
        success: function onSuccess(data, status) {
            alert("답변을 추가했습니다!");
            var addTemplate = $("#commentTemplate").html();
            var template = addTemplate.format(data.response.userId, data.response.content,
                data.response.createdDate, data.response.commentId);
            $(".comments").append(template);
            $(".comment-add textarea").val("");
        }
    });
}

function deleteComment(e) {
    e.preventDefault();
    var deleteBtn = $(this);
    var url = deleteBtn.parent().attr('action');

    $.ajax({
        type: 'delete',
        url: url,
        error: onError,
        success: function onSuccess(data, status) {
            deleteBtn.closest('div.comment').remove();
        }
    });
}

function editRequest(e) {
    e.preventDefault();
    var url = $(this).parent().attr('action');
    var originalComment = $(this).closest('.comment__header');

    $.ajax({
        type : 'get',
        url : url,
        error : onError,
        success : function onSuccess(data, staus) {
            var commentEditTemplate = $('#commentEditTemplate').html().format(url, data.response.content);
            originalComment.html(commentEditTemplate);
        }
    });
}

function editComment(e) {
    e.preventDefault();
    var url = $(this).parent().attr('action');
    var comment = $(this).parent().serialize();
    var updateForm = $(this).closest('div.comment');

    $.ajax({
        type: 'put',
        url: url,
        data: comment,
        dataType: 'json',
        error: onError,
        success: function onSuccess(data, status) {
            alert("답변을 수정했습니다!");
            var commentTemplate = $('#commentTemplate').html();
            var template = commentTemplate.format(data.response.userId, data.response.content,
                data.response.createdDate, data.response.commentId);
            updateForm.replaceWith(template);
        }
    });
}


function onError(xhr, status) {
    alert("실패하였습니다!");
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};