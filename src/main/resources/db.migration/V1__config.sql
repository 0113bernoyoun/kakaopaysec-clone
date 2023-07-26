CREATE TABLE stock_config (
                       id INT NOT NULL AUTO_INCREMENT,
                       init_yn tinyint(1) default false,
                       PRIMARY KEY (id)
);

insert into stock_config(init_yn) values(false);