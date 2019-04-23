package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.enums.AmountType;
import com.enenim.scaffold.model.dao.Service;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ServiceRequest extends RequestBody<Service>{

	@NotBlank(message = "@{service.name.required}")
	private String name;

	@NotBlank(message = "@{service.amount_type.required}")
	@JsonProperty("amount_type")
	private AmountType amountType = AmountType.VARIABLE_AMOUNT;

	@NotBlank(message = "@{service.amount.required}")
	private double amount = 0.00;

	@NotBlank(message = "@{service.discount.required}")
	private double discount = 0.00;

	@NotBlank(message = "@{service.description.required}")
	private String description;

	@NotBlank(message = "@{service.category.required}")
	@JsonProperty("category")
	private String category;

	@NotBlank(message = "@{service.currency.required}")
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
