package pro.sky.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pro.sky.telegrambot.repositories.TaskRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class TelegramBotApplication {


	public static void main(String[] args) {

		SpringApplication.run(TelegramBotApplication.class, args);

	}


}
