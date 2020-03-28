INSERT INTO user (id, user_id, password, name) values (1, 'javajigi', '123456', '자바지기');
INSERT INTO user (id, user_id, password, name) values (2, 'sanjigi', '123456', '산지기');

INSERT INTO milestone (id, subject, start_date, end_date) values (1, '첫번째 프로젝트 기한', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO milestone (id, subject, start_date, end_date) values (2, '두번째 프로젝트 기한', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO label (id, subject) values (1, '라벨1');
INSERT INTO label (id, subject) values (2, '라벨2');
INSERT INTO label (id, subject) values (3, '라벨3');

INSERT INTO issue (id, subject, comment, writer_id, deleted, milestone_id, assignee_id, label_id) values (1, '이슈1 제목입니다', '이슈1 내용입니다', 1, false, null, null, 1);
INSERT INTO issue (id, subject, comment, writer_id, deleted, milestone_id, assignee_id, label_id) values (2, '이슈2 제목입니다', '이슈2 내용입니다', 1, true, null, null, null);
INSERT INTO issue (id, subject, comment, writer_id, deleted, milestone_id, assignee_id, label_id) values (3, '이슈3 제목입니다', '이슈3 내용입니다', 2, false, 1, null, null);

INSERT INTO comment (id, content, user_id, issue_id, deleted, create_date, modified_date) values (1, '댓글달았어요1', 1, 1, false, CURRENT_TIMESTAMP(), null);
INSERT INTO comment (id, content, user_id, issue_id, deleted, create_date, modified_date) values (2, '삭제된댓글달았어요', 1, 1, true, CURRENT_TIMESTAMP(), null)
INSERT INTO comment (id, content, user_id, issue_id, deleted, create_date, modified_date) values (3, '댓글달았어요2', 2, 1, false, CURRENT_TIMESTAMP(), null)
