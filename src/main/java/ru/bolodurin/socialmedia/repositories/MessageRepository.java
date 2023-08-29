package ru.bolodurin.socialmedia.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bolodurin.socialmedia.entities.Message;
import ru.bolodurin.socialmedia.entities.User;

import java.util.Optional;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long>,
        JpaRepository<Message, Long> {

    Optional<Page<Message>> findAllByProducer(User producer, Pageable pageable);

    @Query(value =
            "SELECT m FROM Message m WHERE " +
                    "(m.consumer = :sender AND m.producer = :receiver) " +
                    "OR (m.consumer = :receiver AND m.producer = :sender)")
    Optional<Page<Message>> findChatBetween(@Param("sender") User user,
                                            @Param("receiver") User consumer,
                                            Pageable pageable);

}
