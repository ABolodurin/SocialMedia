package ru.bolodurin.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.bolodurin.socialmedia.entities.Post;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long>, JpaRepository<Post, Long> {

}
