CREATE TABLE tasks (
                       id SERIAL PRIMARY KEY,
                       userId INTEGER NOT NULL,
                       chatId INTEGER NOT NULL,
                       text text NOT NULL ,
                       timeReminder DATE
)