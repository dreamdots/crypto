package com.dots.crypto.repository;

import com.dots.crypto.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>, RepositoryService<Subscription, Integer> {
    Optional<Subscription> findByUser_ChatIdAndToken_Contract(final long chatId, final String contract);
    boolean existsByUser_ChatIdAndToken_Contract(final long chatId, final String contract);
    List<Subscription> findAllByUser_ChatId(final long chatId);
}
