package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.SettledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "settlements")
public class Settlement extends BaseModel{

    @Transient
    public static String searchables = "id,reserved,amount,attempts,benefactorType,description,referenceNo,reportedStatus,traceId";

    @Transient
    public static String table = "settlements";

    @Transient
    public static String references = "currency:currencies";  //attribute:tablename

    @Column(unique = true, length = 100)
    @JsonProperty("reference_no")
    private String referenceNo;

    @Column(length = 40)
    @JsonProperty("trace_id")
    private String traceId;

    @NotNull
    @ManyToOne
    @JoinColumn
    private  Currency currency;

    @Column(length = 10)
    @JsonProperty("benefactor_id")
    private Integer benefactorId;

    @Column(length = 20)
    @JsonProperty("benefactor_type")
    private String benefactorType;

    private String description;

    @NotNull
    private Double amount;

    private String reserved;

    @NotNull
    @Column(length = 11)
    @JsonProperty("split_count")
    private Integer splitCount;

    @NotNull
    private SettledStatus settled = SettledStatus.UNSETTLED;

    @NotNull
    @Column(length = 11)
    private Integer attempts;

    @NotNull
    @Column(length = 50)
    @JsonProperty("reported_status")
    private String reportedStatus;

    @JsonBackReference
    @OneToMany(mappedBy = "settlement", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "settlement", fetch = FetchType.LAZY)
    private Set<TransactionChargeSplit> transactionChargeSplits = new HashSet<>();
}
