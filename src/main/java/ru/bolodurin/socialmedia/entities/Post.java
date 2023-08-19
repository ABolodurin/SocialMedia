package ru.bolodurin.socialmedia.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
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
//    @ManyToOne
//    private User user;


    public Post(String header, String content) {
        this.header = header;
        this.content = content;
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
