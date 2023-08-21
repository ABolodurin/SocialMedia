package ru.bolodurin.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolodurin.socialmedia.entities.Subscription;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscription, Long> {
    //TODO REFACTOR Do I really need it?
}
