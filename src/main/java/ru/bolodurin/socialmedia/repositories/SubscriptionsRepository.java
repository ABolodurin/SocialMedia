package ru.bolodurin.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolodurin.socialmedia.entities.Subscription;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscription, Long> {
//    Optional<List<Subscription>>
}
