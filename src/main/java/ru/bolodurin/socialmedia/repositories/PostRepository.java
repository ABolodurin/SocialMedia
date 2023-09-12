package ru.bolodurin.socialmedia.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.User;

import java.util.Optional;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long>, JpaRepository<Post, Long> {
    Optional<Page<Post>> findAllByUserOrderByTimestampDesc(User user, Pageable pagable);

}
