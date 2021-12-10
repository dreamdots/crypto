package com.dots.crypto.repository;

import com.dots.crypto.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long>, RepositoryService<TelegramUser, Long> {

    @Query(
            value = "insert into crypto_monitor.public.users_subscriptions(telegram_user_chat_id, subscriptions_id) values (:chatId, :subscriptionId)",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    void addNewSubscription(final long chatId, final long subscriptionId);

    @Query(
            value = "delete from crypto_monitor.public.users_subscriptions where telegram_user_chat_id = :chatId and subscriptions_id = :subscriptionId",
            nativeQuery = true
    )
    @Modifying
    @Transactional
    void removeSubscription(final long chatId, final long subscriptionId);
}
