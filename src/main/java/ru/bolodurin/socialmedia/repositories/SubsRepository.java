package ru.bolodurin.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bolodurin.socialmedia.entities.User;

@Repository
public interface SubsRepository extends JpaRepository<User, String> {
    @Query(value =
            "SELECT CASE WHEN COUNT(u) = 2 THEN true ELSE false END " +
                    "FROM User u WHERE " +
                    "(u = :user AND :other MEMBER OF u.subscriptions) " +
                    "OR (u = :other AND :user MEMBER OF u.subscriptions)")
    boolean checkIsFriends(@Param("user") User user, @Param("other") User other);

}
