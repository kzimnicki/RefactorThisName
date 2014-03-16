CREATE TABLE translation (
  id bigserial primary key,
  sourceLang text,
  sourceWord text NOT NULL,
  transLang text,
  transWord text,
  UNIQUE (sourceLang,transLang,sourceWord)
);