FROM mariadb:10.6.13-focal

ENV MYSQL_ROOT_PASSWORD 1q2w3e4r!!

ADD ./mysql-init-files /docker-entrypoint-initdb.d

RUN echo [mysqld] >> /etc/mysql/conf.d/docker.cnf
RUN echo lower_case_table_names=1 >> /etc/mysql/conf.d/docker.cnf
RUN echo default-time-zone='+9:00' >> /etc/mysql/conf.d/docker.cnf
RUN echo collation-server = utf8mb4_unicode_ci >> /etc/mysql/conf.d/docker.cnf
RUN echo character-set-server = utf8mb4 >> /etc/mysql/conf.d/docker.cnf
RUN echo skip-character-set-client-handshake >> /etc/mysql/conf.d/docker.cnf