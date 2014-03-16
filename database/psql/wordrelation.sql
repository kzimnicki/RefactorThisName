CREATE TABLE wordrelation (
  id bigserial primary key,
  rootWord_id bigint NOT NULL REFERENCES rootword (id),
  word_id bigint NOT NULL REFERENCES word (id),
  UNIQUE (word_id,rootWord_id)
);