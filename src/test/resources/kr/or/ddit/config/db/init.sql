-- 게시글 시퀀스 생성, 삭제
DROP SEQUENCE post_id;

create SEQUENCE post_id;

-- 데이터 추가, 삭제

DELETE POST WHERE POST_ID=1;


-- 댓글 시퀀스 생성, 삭제
DROP SEQUENCE tag_id;

create SEQUENCE tag_id;

DELETE TAG WHERE TAG_ID=1;

-- 게시판 시퀀스 생성, 삭제

DROP SEQUENCE board_id;

create SEQUENCE board_id
start with 2;

DELETE BOARD WHERE BOARD_ID=2;

