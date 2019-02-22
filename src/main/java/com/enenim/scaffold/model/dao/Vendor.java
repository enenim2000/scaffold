package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.VendorType;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.interfaces.IAudit;
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
@Table(name = "vendors")
public class Vendor extends BaseModel implements IAudit{

    @Transient
    public static String searchables = "name,address,email,phoneNumber,logo,code,testSecret,secret,tradingName,slug,parentId";

    @Transient
    public static String table = "vendors";

    @Transient
    public static String references = "";

    @Transient
    Login login;

    public Vendor() {
    }

    public Vendor(Long Id) {
        super();
        this.setId(id);
    }

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String address;

    @NotNull
    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 20)
    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    private String phoneNumber;

    @Column(unique = true, length = 100)
    private String code;

    @Column(unique = true, length = 100)
    @JsonProperty("test_secret")
    @SerializedName("test_secret")
    private String testSecret;

    @Column(unique = true, length = 100)
    private String secret;

    @Column(unique = true, length = 100)
    @JsonProperty("trading_name")
    @SerializedName("trading_name")
    private String tradingName;

    @Column(unique = true, length = 50)
    private String slug;

    @JsonProperty("logo_path")
    @SerializedName("logo_path")
    private String logoPath;

    @NotNull
    private VendorType type = VendorType.REGULAR;

    @NotNull
    private VerifyStatus verified = VerifyStatus.NOT_VERIFIED;

    @NotNull
    private EnabledStatus enabled = EnabledStatus.DISABLED;

    @OneToOne(mappedBy = "vendor",cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonProperty("vendor_account")
    @SerializedName("vendor_account")
    private VendorAccount vendorAccount;

    @ManyToMany
    @JoinTable(name = "vendor_category",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"vendor_id", "category_id"})
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "vendor_payment_channel",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_channel_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"vendor_id", "payment_channel_id"})
    )
    @JsonProperty("payment_channels")
    @SerializedName("payment_channels")
    private Set<PaymentChannel> paymentChannels = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "vendor_payment_method",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"vendor_id", "payment_method_id"})
    )
    @JsonProperty("payment_methods")
    @SerializedName("payment_methods")
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    private Set<VendorSetting> vendorSettings = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "vendor", fetch = FetchType.EAGER)
    private Set<Service> services = new HashSet<>();

    public void setCommonProperties(BCryptPasswordEncoder bCryptPasswordEncoder){
        setSlug(RandomStringUtils.randomAlphanumeric(30));
        setTestSecret(bCryptPasswordEncoder.encode(new Date().toString() + Math.random()));
        setSecret(bCryptPasswordEncoder.encode(new Date().toString() + Math.random()));
        setCode(RandomStringUtils.randomAlphanumeric(10));
    }
}