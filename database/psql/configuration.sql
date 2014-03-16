CREATE TABLE configuration (
  id bigserial primary key,
  created timestamp DEFAULT NULL,
  updated timestamp DEFAULT NULL,
  isPhrasalVerbAdded boolean NOT NULL,
  max int NOT NULL,
  min int NOT NULL,
  phrasalVerbTemplate text NOT NULL,
  subtitleProcessor text DEFAULT NULL,
  subtitleTemplate text NOT NULL,
  textTemplate text NOT NULL
);