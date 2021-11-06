package com.dots.crypto.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    private String hash;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "timestamp")
    private Date timestamp;

    private Long nonce;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "transaction_index")
    private Long transactionIndex;

    @Column(name = "_from")
    private String from;

    @Column(name = "_to")
    private String to;

    private Long value;

    private Long gas;

    @Column(name = "gas_price")
    private Long gasPrice;

    @Column(name = "is_error")
    private Integer isError;

    @Column(name = "tx_receipt_status")
    private Integer txReceiptStatus;

    @Column(name = "contract_address")
    private String contractAddress;

    @Column(name = "cumulative_gas_value")
    private Long cumulativeGasValue;

    @Column(name = "gas_used")
    private Long gasUsed;

    private Long confirmations;
}
