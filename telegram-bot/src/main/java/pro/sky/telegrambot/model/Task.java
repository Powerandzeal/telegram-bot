package pro.sky.telegrambot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Task {
    public Task() {
    }

    public Task(Long id, Long chatId, String text, LocalDateTime timeReminder) {
        this.id = id;
        this.chatId = chatId;
        this.text = text;
        this.timeReminder = timeReminder;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "time_reminder", nullable = false)
    private LocalDateTime timeReminder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimeReminder() {
        return timeReminder;
    }

    public void setTimeReminder(LocalDateTime timeReminder) {
        this.timeReminder = timeReminder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(chatId, task.chatId) && Objects.equals(text, task.text) && Objects.equals(timeReminder, task.timeReminder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, text, timeReminder);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", text='" + text + '\'' +
                ", timeReminder=" + timeReminder +
                '}';
    }
}
