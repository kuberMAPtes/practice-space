DROP TABLE IF EXISTS comment CASCADE;

DROP TABLE IF EXISTS feed CASCADE;

DROP TABLe IF EXISTS member;

CREATE TABLE member (
    member_id VARCHAR(255) NOT NULL PRIMARY KEY,
    member_name VARCHAR(255) NOT NULL
);

ALTER TABLE member ADD COLUMN reg_date DATETIME AFTER member_name;

CREATE TABLE feed (
    feed_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) DEFAULT ''
);

ALTER TABLE feed AUTO_INCREMENT = 10000;

ALTER TABLE feed ADD COLUMN writer VARCHAR(255) NOT NULL AFTER content;

ALTER TABLE feed ADD CONSTRAINT writer_fk FOREIGN KEY (writer) REFERENCES member (member_id);

CREATE TABLE bookmark (
    member_id VARCHAR(255) NOT NULL,
    feed_id BIGINT NOT NULL,
    CONSTRAINT member_id_fk FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT feed_id_fk FOREIGN KEY (feed_id) REFERENCES feed (feed_id)
);

CREATE TABLE comment (
    tag_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    register VARCHAR(255) NOT NULL,
    feed_id BIGINT NOT NULL,
    CONSTRAINT register_fk FOREIGN KEY (register) REFERENCES member (member_id),
    CONSTRAINT feed_kf FOREIGN KEY (feed_id) REFERENCES feed (feed_id)
);

ALTER TABLE comment AUTO_INCREMENT = 10000;