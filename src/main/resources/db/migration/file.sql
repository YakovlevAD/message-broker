SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS c_users;

CREATE TABLE c_users (
                         id  INT(11) PRIMARY KEY AUTO_INCREMENT,
                         uid VARCHAR(255),
                         token VARCHAR(255),
                         username VARCHAR(255),
                         email VARCHAR(255),
                         description VARCHAR(255),
                         status INT(2)
)AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS c_chats;

CREATE TABLE c_chats (
                         id  INT(11) PRIMARY KEY AUTO_INCREMENT,
                         description VARCHAR(255),
                         type INT(2)
)AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS c_subscribers;

CREATE TABLE c_subscribers (
                               chat_id INT(11),
                               id_subscriber VARCHAR(255) NOT NULL,
                               CONSTRAINT FK_SUB_ID_01 FOREIGN KEY (chat_id)
                                   REFERENCES c_chats (id)
)DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS c_messages;

CREATE TABLE c_messages (
                            id INT(11) AUTO_INCREMENT,
                            message_text  VARCHAR(255) NOT NULL,
                            sender_id  VARCHAR(255) NOT NULL,
                            chat_id  INT(11) NOT NULL,
                            created_datetime VARCHAR(255) NOT NULL,
                            sender_name VARCHAR (255) NOT NULL,
                            status INT(11) NOT NULL,
                            CONSTRAINT FK_CHAT_ID FOREIGN KEY (chat_id)
                                REFERENCES c_chats (id),
                            PRIMARY KEY (id)
)AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS c_events;

CREATE TABLE c_events (
                          id INT(11) AUTO_INCREMENT,
                          owner_id VARCHAR (255) NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          duration VARCHAR(255) NOT NULL,
                          start_time VARCHAR(255) NOT NULL,
                          chat_id INT(11) NOT NULL,
                          latitude VARCHAR(255) NOT NULL,
                          longitude VARCHAR(255) NOT NULL,

                          PRIMARY KEY (id)
)AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;

select * from c_messages;

select * from c_chats join c_subscribers cs on c_chats.id = cs.chat_id;