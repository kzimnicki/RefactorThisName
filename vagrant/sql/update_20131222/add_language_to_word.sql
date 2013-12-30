ALTER TABLE word ADD COLUMN language INT(11) DEFAULT 0 NOT NULL;
ALTER TABLE word MODIFY value VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL;
ALTER TABLE word DROP INDEX value;
CREATE UNIQUE INDEX value ON word(value, wordType, language);