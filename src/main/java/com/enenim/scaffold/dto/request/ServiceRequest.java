package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.enums.AmountType;
import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class ServiceRequest extends RequestBody<Service>{

	@NotBlank
	private String name;

	@NotBlank
	@JsonProperty("amount_type")
	private AmountType amountType = AmountType.VARIABLE_AMOUNT;

	@NotBlank
	private double amount = 0.00;

	@NotBlank
	private double surcharge = 0.00;

	@NotBlank
	private double discount = 0.00;

	@NotBlank
	private String description;

	@NotBlank
	private String slug;

	@NotBlank
	@JsonProperty("category_ids")
	private List<Long> categoryIds;

	@NotBlank
	@JsonProperty("vendor_id")
	private Long vendorId;

	@NotBlank
	@JsonProperty("currency_id")
	private Long currencyId;

	@Override
	public Service buildModel() {
		return ObjectMapperUtil.map(this, Service.class);
	}

	@Override
	public Service buildModel(Service service) {
		return ObjectMapperUtil.map(this, service);
	}
}
