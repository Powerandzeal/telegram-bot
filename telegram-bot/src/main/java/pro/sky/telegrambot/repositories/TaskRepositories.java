package pro.sky.telegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Task;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface TaskRepositories extends JpaRepository<Task, Long> {


    Collection<Task> findTaskByTimeReminder(LocalDateTime localDateTime);


    Task getTopBy();


}
