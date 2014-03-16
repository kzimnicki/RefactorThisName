CREATE TABLE rootword (
  id bigserial primary key,
  rootWord_id bigint NOT NULL UNIQUE REFERENCES word (id)
);