CREATE TABLE user_data (
  id bigserial primary key,
  created timestamp,
  updated timestamp,
  enabled boolean NOT NULL,
  password text NOT NULL,
  role text NOT NULL,
  username text NOT NULL UNIQUE,
  config_id bigint DEFAULT NULL REFERENCES configuration (id)
);
