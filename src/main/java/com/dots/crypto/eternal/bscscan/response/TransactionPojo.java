package com.dots.crypto.eternal.bscscan.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionPojo {
    private long blockNumber;
    @JsonProperty("timeStamp")
    private long timestamp;
    private String hash;
    private long nonce;
    private String blockHash;
    private long transactionIndex;
    private String from;
    private String to;
    private long value;
    private long gas;
    private long gasPrice;
    private int isError;
    private int txReceiptStatus;
    private String contractAddress;
    private long cumulativeGasValue;
    private long gasUsed;
    private long confirmations;
}
