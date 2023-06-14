CREATE TABLE todos (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL ,
    user_id bigint(20),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);