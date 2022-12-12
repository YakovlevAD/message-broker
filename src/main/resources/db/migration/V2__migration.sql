SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS chats;

CREATE TABLE chats (
                       id_chat VARCHAR(255) NOT NULL
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS subscribers;

CREATE TABLE subscribers (
    id_chat VARCHAR(255) NOT NULL,
    id_subscriber VARCHAR(255) NOT NULL
) DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS messages;

CREATE TABLE messages (
    id            VARCHAR(255) NOT NULL,
    message_text  VARCHAR(255) NOT NULL,
    message_from  VARCHAR(255) NOT NULL,
    message_to    VARCHAR(255) NOT NULL,
    created_datetime VARCHAR(255) NOT NULL
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS cEvents;

CREATE TABLE cEvents (
    id VARCHAR(255) NOT NULL,
    ownerId VARCHAR (255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    duration VARCHAR(255) NOT NULL,
    startTime VARCHAR(255) NOT NULL,
    chatId VARCHAR(255) NOT NULL,
    latitude VARCHAR(255) NOT NULL,
    longitude VARCHAR(255) NOT NULL
) DEFAULT CHARSET =utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
