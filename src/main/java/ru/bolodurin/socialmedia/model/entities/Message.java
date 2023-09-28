package ru.bolodurin.socialmedia.model.entities;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String message;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "consumer")
    private User producer;

    @ManyToOne
    @JoinColumn(name = "producer")
    private User consumer;

    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    public Message(String message, User consumer, User producer) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.producer = producer;
        this.consumer = consumer;
    }

}
