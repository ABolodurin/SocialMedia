package ru.bolodurin.socialmedia.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post
//        implements Comparable<Post>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;                                    //Long??
    @Column(name = "header")
    private String header;
    @Setter
    @Column(name = "text")
    private String content;

//    private Image????

    //    private final timeStamp???
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Post(String header, String content, User user) {
        this.header = header;
        this.content = content;
//        this.user = user;
        //timeStamp
    }

    public Post() {
        //timeStamp
    }

//    @Override
//    public int compareTo(Post o) {
//        return 0
////        this.timeStamp - o.timeStamp
//        ;
//    }
}
