package com.enenim.scaffold.model.dao;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.VendorType;
import com.enenim.scaffold.enums.VerifyStatus;
import com.enenim.scaffold.interfaces.IAudit;
import com.enenim.scaffold.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vendors")
public class Vendor extends BaseModel implements IAudit{

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
    @JsonProperty("office_hour")
    @SerializedName("office_hour")
    private String officeHour;

    @Column(length = 20)
    @JsonProperty("company_size")
    @SerializedName("company_size")
    private String companySize;

    @Column(length = 20)
    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    private String phoneNumber;

    @Column(length = 25)
    @JsonProperty("tel")
    private String tel;

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

    @JsonProperty("youtube_url")
    @SerializedName("youtube_url")
    private String youtubeUrl;

    @JsonProperty("facebook_url")
    @SerializedName("facebook_url")
    private String facebookUrl;

    @JsonProperty("twitter_url")
    @SerializedName("twitter_url")
    private String twitterUrl;

    @JsonProperty("logo_url")
    @SerializedName("logo_url")
    private String logoUrl;

    @Column(unique = true, length = 50)
    private String slug;

    /**
     * This column stores the category key
     */
    @JsonProperty("category")
    private String category;

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
    @ApiModelProperty(required = true, hidden = true)
    private VendorAccount vendorAccount;

    @OneToOne(mappedBy = "vendor", cascade = CascadeType.ALL)
    @JsonProperty("contact")
    @SerializedName("contact")
    @JsonManagedReference
    private Contact contact;

    @ManyToMany
    @JoinTable(name = "vendor_payment_channel",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_channel_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"vendor_id", "payment_channel_id"})
    )
    @JsonProperty("payment_channels")
    @SerializedName("payment_channels")
    @ApiModelProperty(required = true, hidden = true)
    private Set<PaymentChannel> paymentChannels = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "vendor_category",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"vendor_id", "category_id"})
    )
    @JsonProperty("categories")
    @SerializedName("categories")
    @ApiModelProperty(required = true, hidden = true)
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "vendor_payment_method",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"vendor_id", "payment_method_id"})
    )
    @JsonProperty("payment_methods")
    @SerializedName("payment_methods")
    @ApiModelProperty(required = true, hidden = true)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    @ApiModelProperty(required = true, hidden = true)
    private Set<VendorSetting> vendorSettings = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "vendor", fetch = FetchType.EAGER)
    @ApiModelProperty(required = true, hidden = true)
    private Set<Service> services = new HashSet<>();

    public void setCommonProperties(){
        setSlug(RandomStringUtils.randomAlphanumeric(20).toUpperCase().toUpperCase());
        setTestSecret("TEST|" + RandomStringUtils.randomAlphanumeric(30).toUpperCase());
        setSecret("LIVE|" + RandomStringUtils.randomAlphanumeric(30).toUpperCase());
        setCode(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
    }
}