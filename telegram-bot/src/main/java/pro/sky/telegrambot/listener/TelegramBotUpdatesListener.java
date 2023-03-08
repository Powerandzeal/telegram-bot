package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repositories.TaskRepositories;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private static final Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Autowired
    private TaskRepositories taskRepositories;
    @Autowired
    private TelegramBot telegramBot;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) { // метод для автоматического ответа на сообщение /start
        updates.forEach(update -> {

            logger.info("Processing update: {}", update);
            Long chatIdUser = update.message().chat().id();
            String message1 = update.message().text();
            Matcher matcher = pattern.matcher(message1);

            if ("/start".equals(message1)) {
                telegramBot.execute(new SendMessage(chatIdUser, "Welcome to Remainder habits bot " +
                        update.message().chat().username() + " Enter your reminder in format " + '\n' +
                        "01.01.2022 20:00 Сделать домашнюю работу"));
            }


            logger.info("сообщение от пользователя" + message1);
            if (matcher.matches()) {
                // обрабатываем ситуацию, когда строка соответствует паттерну
                Task task = new Task();
                task.setText(matcher.group(3));
                task.setChatId(chatIdUser);
                task.setTimeReminder(LocalDateTime.parse(matcher.group(1), DATE_TIME_FORMATTER));
                taskRepositories.save(task);
                telegramBot.execute(new SendMessage(chatIdUser, "Your Task is add"));

            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}



