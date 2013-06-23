alter table user modify password VARCHAR(32) ;
update user SET password = md5(concat(username, password));
