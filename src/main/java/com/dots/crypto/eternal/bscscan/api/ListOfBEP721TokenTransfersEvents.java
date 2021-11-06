package com.dots.crypto.eternal.bscscan.api;

import com.dots.crypto.eternal.bscscan.RequestAction;
import com.dots.crypto.eternal.bscscan.RequestModule;
import com.dots.crypto.eternal.bscscan.RequestSort;
import com.dots.crypto.eternal.bscscan.response.TransactionPojo;
import com.dots.crypto.eternal.common.ApiRequest;
import com.dots.crypto.eternal.common.RequestMethod;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListOfBEP721TokenTransfersEvents extends ApiRequest<List<TransactionPojo>> {
    private final JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, TransactionPojo.class);
    private final RequestAction action = RequestAction.TOKENNFTTX;
    private final RequestModule module = RequestModule.ACCOUNT;
    private final RequestMethod method = RequestMethod.GET;

    private String contractAddress;
    private String address;
    private int startBlock;
    private int endBlock;
    private int page;
    private int offset;
    private String sort;

    public static ListOfBEP721TokenTransfersEvents byAddress(final String address,
                                                             final RequestSort sort) {
        final ListOfBEP721TokenTransfersEvents pojo = new ListOfBEP721TokenTransfersEvents();

        pojo.setAddress(address);
        pojo.setContractAddress(null);
        pojo.setOffset(10000);
        pojo.setPage(1);
        pojo.setEndBlock(0);
        pojo.setStartBlock(99999999);
        pojo.setSort(sort.getSort());

        return pojo;
    }
}
