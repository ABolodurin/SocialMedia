package ru.bolodurin.socialmedia.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
