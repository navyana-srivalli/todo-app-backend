CREATE TABLE user_todo (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20),
    todo_id bigint(20),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (todo_id) REFERENCES todos(id),
    PRIMARY KEY (id)
);
