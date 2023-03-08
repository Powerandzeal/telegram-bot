package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repositories.TaskRepositories;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
@Configuration
public class Scheduling {

    @Autowired
    TelegramBot telegramBot;
    @Autowired
    TaskRepositories taskRepositories;


    @Scheduled(cron = "0 0/1 * * * *")
    public void sendMessage1() {
        var task = taskRepositories.findTaskByTimeReminder(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        for (Task tasks : task) {
            SendMessage reminder = new SendMessage(tasks.getChatId(), tasks.getText());
            telegramBot.execute(reminder);
        }
    }


//    @Scheduled(cron = "0 * * * * *")
//    public void run1() {
//
//        SendMessage reminder = new SendMessage(519350974, "это сообщение демон" +
//                " которое отправляется раз в минуту" );
//            telegramBot.execute(reminder);
//
//    }


}
