CREATE TABLE user_excludedwords (
  id bigserial primary key,
  created timestamp DEFAULT NULL,
  rootWord_id bigint DEFAULT NULL REFERENCES rootword (id),
  user_id bigint DEFAULT NULL REFERENCES user_data (id)
);