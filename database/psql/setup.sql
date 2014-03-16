\set ON_ERROR_STOP on

DROP TABLE IF EXISTS user_excludedwords;
DROP TABLE IF EXISTS user_includedwords;
DROP TABLE IF EXISTS user_data;
DROP TABLE IF EXISTS configuration;
DROP TABLE IF EXISTS translation;
DROP TABLE IF EXISTS wordrelation;
DROP TABLE IF EXISTS rootword;
DROP TABLE IF EXISTS word;

\i configuration.sql
\i user_data.sql
\i translation.sql
\i word.sql
\i rootword.sql
\i wordrelation.sql
\i user_excludedwords.sql
\i user_includedwords.sql

SET standard_conforming_strings = off;

\i ../data/configuration_data.sql
\i ../data/user_data.sql
\i ../data/translation_data.sql
\i ../data/word_data.sql
\i ../data/rootword_data.sql
\i ../data/wordrelation_data.sql
\i ../data/user_excludedwords_data.sql
\i ../data/user_includedwords_data.sql


