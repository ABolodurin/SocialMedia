package ru.bolodurin.socialmedia.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder //TODO custom
@Entity
@Table(name = "posts")
public class Post
//        implements Comparable<Post>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "header")
    private String header;
    @Setter
    @Column(name = "content")
    private String content;
    @Column(name = "timestamp")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Post(Long id, String header, String content, LocalDateTime timestamp, User user) {
        this.header = header;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.user = user;
    }

    public Post() {
        this.timestamp = LocalDateTime.now();
    }

//    @Override
//    public int compareTo(Post o) {
//       return o.timestamp.compareTo(this.timestamp);
//    }
}
