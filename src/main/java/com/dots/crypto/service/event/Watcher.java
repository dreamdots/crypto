package com.dots.crypto.service.event;

import com.dots.crypto.eternal.bscscan.BscscanClient;
import com.dots.crypto.eternal.bscscan.RequestSort;
import com.dots.crypto.eternal.bscscan.api.ListOfBEP721TokenTransfersEvents;
import com.dots.crypto.eternal.bscscan.api.ListOfInternalTransactionsPojo;
import com.dots.crypto.eternal.bscscan.api.ListOfNormalTransactionsPojo;
import com.dots.crypto.eternal.bscscan.response.TransactionPojo;
import com.dots.crypto.eternal.common.ApiRequest;
import com.dots.crypto.model.TelegramUser;
import com.dots.crypto.model.Token;
import com.dots.crypto.repository.SubscriptionRepository;
import com.dots.crypto.repository.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Watcher {
    private final TelegramUserRepository telegramUserRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BscscanClient apiClient;

    @Value("${watcher.enable}")
    private boolean enable;

    @Scheduled(initialDelayString = "${watcher.refresh_time}", fixedDelayString = "${watcher.refresh_time}")
    public void watch() {
        if (enable) {
            final List<TelegramUser> telegramUsers = telegramUserRepository.findAll();
            telegramUsers
                    .parallelStream()
                    .flatMap(user -> {
                        log.info("Watch actual transaction for user -> " + user.getChatId());
                        return subscriptionRepository.findAllByTelegramUser_ChatId(user.getChatId()).stream();
                    })
                    .forEach(subscription -> {
                        log.info("Collect info for token -> " + subscription.getToken().toString());
                        collect(subscription.getToken(), subscription.getThreshold());
                    });
        }
    }

    public void collect(final Token token,
                        final long threshold) {
        try {
            final String contract = token.getContract();

            final CopyOnWriteArrayList<TransactionPojo> allTrans = transactionRequestsByContract(contract)
                    .parallelStream()
                    .flatMap(r -> r.execute(apiClient).deserialize().or(Collections.emptyList()).stream())
                    .filter(t -> t.getValue() >= threshold)
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));

            log.info("" + allTrans.size());

        } catch (Exception e) {
            log.error("Error while collect info for token -> " + token.toString(), e);
        }
    }

    private List<ApiRequest<List<TransactionPojo>>> transactionRequestsByContract(final String contract) {
        final List<ApiRequest<List<TransactionPojo>>> requests = new ArrayList<>();

        requests.add(ListOfNormalTransactionsPojo.byAddress(contract, RequestSort.DESC));
        requests.add(ListOfInternalTransactionsPojo.byAddress(contract, RequestSort.DESC));
        requests.add(ListOfBEP721TokenTransfersEvents.byAddress(contract, RequestSort.DESC));

        return requests;
    }
}
