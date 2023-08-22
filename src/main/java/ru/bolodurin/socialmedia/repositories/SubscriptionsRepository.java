package ru.bolodurin.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bolodurin.socialmedia.entities.Subscription;

import java.util.Optional;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscription, Long> {
    @Query(value = "SELECT * FROM subscriptions WHERE subscriber = :sub AND subscription = :subTo"
            , nativeQuery = true)
    Optional<Subscription> findSubscriptionBySubscriber(
            @Param("sub") String subscriberUsername,
            @Param("subTo") String subscriptionUsername);

}
