package pro.sky.telegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Task;

import javax.persistence.Id;

@Repository
public interface TaskRepositories extends JpaRepository<Task, Long> {



}
