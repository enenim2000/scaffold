package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.BillerType;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "billers")
public class Biller extends BaseModel {

    @Transient
    public static String searchables = "name,address,email,phoneNumber,logo,code,testSecret,secret,tradingName,slug,parentId";

    @Transient
    public static String table = "billers";

    @Transient
    public static String references = "";

    @Transient
    Login login;

    public Biller() {
    }

    public Biller(Long Id) {
        super();
        this.setId(id);
    }

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    @Column(length = 100)
    private String address;

    @NotNull
    @Column(unique = true, length = 100)
    private String email;

    @NotNull
    @Column(length = 20)
    private String phone;

    private String logo;

    @NotNull
    @Column(unique = true, length = 100)
    private String code;

    @NotNull
    @Column(unique = true, length = 100)
    @JsonProperty("test_secret")
    private String testSecret;

    @NotNull
    @Column(unique = true, length = 100)
    private String secret;

    @NotNull
    @Column(unique = true, length = 100)
    @JsonProperty("trading_name")
    private String tradingName;

    @NotNull
    @Column(unique = true, length = 50)
    private String slug;

    @NotNull
    private BillerType type = BillerType.REGULAR;

    @JsonProperty("parent_id")
    private Integer parentId;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @OneToOne(mappedBy = "biller",cascade = CascadeType.ALL)
    @JsonBackReference
    private BillerAccount billerAccount;

    @ManyToMany
    @JoinTable(name = "biller_category",
            joinColumns = @JoinColumn(name = "biller_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"biller_id", "category_id"})
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "biller_payment_channel",
            joinColumns = @JoinColumn(name = "biller_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_channel_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"biller_id", "payment_channel_id"})
    )
    private Set<PaymentChannel> paymentChannels = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "biller_payment_method",
            joinColumns = @JoinColumn(name = "biller_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"biller_id", "payment_method_id"})
    )
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "biller", fetch = FetchType.LAZY)
    private Set<BillerSetting> billerSettings = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "biller", fetch = FetchType.EAGER)
    private Set<Item> items = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "biller", fetch = FetchType.LAZY)
    private Set<SharingFormula> sharingFormulas = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "biller", fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "biller", fetch = FetchType.LAZY)
    private Set<TransactionDemo> transactionDemos = new HashSet<>();

    public String getSecret() {
        return "#################";
    }

    public String getEncodedSecret() {
        return this.secret;
    }
}