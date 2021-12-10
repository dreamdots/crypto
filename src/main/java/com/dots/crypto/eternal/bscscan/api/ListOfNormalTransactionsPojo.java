package com.dots.crypto.eternal.bscscan.api;

import com.dots.crypto.eternal.bscscan.BscscanApiRequest;
import com.dots.crypto.eternal.bscscan.RequestAction;
import com.dots.crypto.eternal.bscscan.RequestModule;
import com.dots.crypto.eternal.bscscan.RequestSort;
import com.dots.crypto.eternal.bscscan.response.TransactionPojo;
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
public class ListOfNormalTransactionsPojo extends BscscanApiRequest<List<TransactionPojo>> {
    private final JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, TransactionPojo.class);
    private final RequestAction action = RequestAction.TXLIST;
    private final RequestModule module = RequestModule.ACCOUNT;
    private final RequestMethod method = RequestMethod.GET;

    private String address;
    private int startBlock;
    private int endBlock;
    private int page;
    private int offset;
    private String sort;

    public static ListOfNormalTransactionsPojo byAddress(final String address,
                                                         final RequestSort sort) {
        final ListOfNormalTransactionsPojo pojo = new ListOfNormalTransactionsPojo();

        pojo.setAddress(address);
        pojo.setOffset(10000);
        pojo.setPage(1);
        pojo.setStartBlock(0);
        pojo.setEndBlock(99999999);
        pojo.setSort(sort.getSort());

        return pojo;
    }
}
