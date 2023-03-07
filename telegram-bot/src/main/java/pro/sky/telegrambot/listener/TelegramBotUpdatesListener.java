package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repositories.TaskRepositories;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    @Autowired
    private TaskRepositories taskRepositories;


    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) { // метод для автоматического ответа на сообщение /start
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (!update.message().text().isEmpty()) {
                Long chatIdUser = update.message().chat().id();

                if ("/start".equals(update.message().text())) {
                    telegramBot.execute(new SendMessage(chatIdUser, "Welcome to Remainder habits bot " +
                            update.message().chat().username() + " Enter your reminder in format " + '\n' +
                            "01.01.2022 20:00 Сделать домашнюю работу"));


                    String message = update.message().text();
                    logger.info("сообщение от пользователя" + message);

                    Matcher matcher = pattern.matcher(message);
                    if (matcher.matches()) {
                        // обрабатываем ситуацию, когда строка соответствует паттерну
                        Task task = new Task();
                        String text = update.message().text();
                        task.setText(text);
                        task.setChatId(chatIdUser);
                        task.setTimeReminder(LocalDateTime.now());
                        taskRepositories.save(task);


                    }
                } else {
                    telegramBot.execute(new SendMessage(chatIdUser, "Sorry I dont understand your command" +
                            "named " + update.message().text()));
                }

            }


        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


//        telegramBot.setUpdatesListener(updates1 -> {
//            Long chatIduser1 =
//            // Обрабатываем входящие сообщения
//            // возвращаем id последнего обработанного сообщения или отмечаем все как обработанные
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        });
// 01.01.2022 20:00 Сделать домашнюю работу


//if (update.message().text().equals("/start")
//                ) {
//        Long chatIdUser = update.message().chat().id();
//        telegramBot.execute(new SendMessage(chatIdUser, "Welcome to Remainder habits bot"));
//
//    }


//-------------------------------------------      код со свитчами
//     if (!update.message().text().isEmpty()) {
//        Long chatIdUser = update.message().chat().id();
//
//        switch (update.message().text()) {
//            case "/start":
//                telegramBot.execute(new SendMessage(chatIdUser, "Welcome to Remainder habits bot " +
//                        update.message().chat().username() + " Enter your reminder in format " + '\n' +
//                        "01.01.2022 20:00 Сделать домашнюю работу"));
//
//                switch (update.message().text()) {
//                    case
//                            String message1 = update.message().text();
//                        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
//
//                        logger.info("сообщение от пользователя" + message1);
//
//                        Matcher matcher = pattern.matcher(message1);
//                        if (matcher.matches()) {
//                            // обрабатываем ситуацию, когда строка соответствует паттерну
//                            Task task = new Task();
//                            String text = update.message().text();
//                            task.setText(text);
//                            task.setChatId(chatIdUser);
//                            task.setTimeReminder(LocalDateTime.now());
//                            taskRepositories.save(task);
//
//                        }
//                }
//                            }
//
//                break;
//            default:
//
//                telegramBot.execute(new SendMessage(chatIdUser, "Sorry I dont understand your command" +
//                        "named " + update.message().text()));
//        }
//    }


//=======================
//     if (update.message().text().equals("/start")) {
//        Long chatIdUser = update.message().chat().id();
//        SendMessage greeting = new SendMessage(chatIdUser,"Welcome to Remainder habits bot " +
//                update.message().chat().username() + " Enter your reminder in format " + '\n' +
//                "01.01.2022 20:00 Сделать домашнюю работу");
//        telegramBot.execute(greeting);
//
////                    String message1 = update.message().text();
//
////                    logger.info("сообщение от пользователя" + message1);
//
//        Matcher matcher = pattern.matcher(update.message().text());
//        if (matcher.matches()) {
//            // обрабатываем ситуацию, когда строка соответствует паттерну
//            LocalDateTime localDateTime = LocalDateTime.parse(matcher.group(1), DATE_TIME_FORMATTER);
//            String text = matcher.group(3);
//            Task task = new Task();
//            task.setText(text);
//            task.setChatId(chatIdUser);
//            task.setTimeReminder(LocalDateTime.now());
//            taskRepositories.save(task);
//        }


}



