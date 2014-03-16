CREATE TABLE word (
  id bigserial primary key,
  frequency int NOT NULL,
  value text NOT NULL,
  wordType int NOT NULL,
  UNIQUE (value,wordType)
);