package ru.bolodurin.socialmedia.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.User;

import java.util.Optional;

@Repository
public interface FeedRepository extends PagingAndSortingRepository<Post, Long>,
        JpaRepository<Post, Long> {
    @Query(
            "SELECT p FROM Post p WHERE :sub MEMBER OF p.user.subscribers")
    Optional<Page<Post>> findPostsBySubscriptionsFromUser(@Param("sub") User sub, Pageable pageable);

}
