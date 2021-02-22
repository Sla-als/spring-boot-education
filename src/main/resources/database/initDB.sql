DROP TABLE IF EXISTS lessons CASCADE;
CREATE TABLE IF NOT EXISTS lessons
(
    id          bigserial PRIMARY KEY,
    title       VARCHAR(64) NOT NULL,
    description varchar(255)
);
DROP TABLE IF EXISTS files CASCADE;
CREATE TABLE IF NOT EXISTS files
(
    id          bigserial PRIMARY KEY,
    path        VARCHAR(64) NOT NULL,
    name        VARCHAR(64) NOT NULL,
    upload_data timestamp
);

DROP TABLE IF EXISTS lesson_files CASCADE;
CREATE TABLE lesson_files
(
    lesson_id bigint NOT NULL,
    file_id   bigint NOT NULL,
    PRIMARY KEY (lesson_id, file_id),
    FOREIGN KEY (lesson_id) REFERENCES lessons (id),
    FOREIGN KEY (file_id) REFERENCES files (id)
);
