CREATE TABLE word (
  id bigserial primary key,
  frequency int NOT NULL,
  value text NOT NULL,
  wordType int NOT NULL,
  UNIQUE (value,wordType)
);
CREATE INDEX word_value_idx ON word(value);