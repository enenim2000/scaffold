package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.BillerType;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    private String phoneNumber;

    @NotNull
    @Column(unique = true, length = 100)
    private String code;

    @NotNull
    @Column(unique = true, length = 100)
    @JsonProperty("test_secret")
    @SerializedName("test_secret")
    private String testSecret;

    @NotNull
    @Column(unique = true, length = 100)
    private String secret;

    @NotNull
    @Column(unique = true, length = 100)
    @JsonProperty("trading_name")
    @SerializedName("trading_name")
    private String tradingName;

    @NotNull
    @Column(unique = true, length = 50)
    private String slug;

    @JsonProperty("logo_path")
    @SerializedName("logo_path")
    private String logoPath;

    @NotNull
    private BillerType type = BillerType.REGULAR;

    @NotNull
    private VerifyStatus verified = VerifyStatus.NOT_VERIFIED;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.ENABLED;

    @OneToOne(mappedBy = "biller",cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonProperty("biller_account")
    @SerializedName("biller_account")
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
    @JsonProperty("payment_channels")
    @SerializedName("payment_channels")
    private Set<PaymentChannel> paymentChannels = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "biller_payment_method",
            joinColumns = @JoinColumn(name = "biller_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"biller_id", "payment_method_id"})
    )
    @JsonProperty("payment_methods")
    @SerializedName("payment_methods")
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

    public void setCommonProperties(BCryptPasswordEncoder bCryptPasswordEncoder){
        setSlug(RandomStringUtils.randomAlphanumeric(30));
        setTestSecret(bCryptPasswordEncoder.encode(new Date().toString() + Math.random()));
        setSecret(bCryptPasswordEncoder.encode(new Date().toString() + Math.random()));
        setCode(RandomStringUtils.randomAlphanumeric(10));
    }
}