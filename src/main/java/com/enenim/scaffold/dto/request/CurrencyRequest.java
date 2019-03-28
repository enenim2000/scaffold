package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Currency;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CurrencyRequest extends RequestBody<Currency>{

	@NotBlank(message = "@{currency.name.required}")
	private String name;

	@NotBlank(message = "@{currency.code.required}")
	private String code;

	@NotBlank(message = "@{currency.html.required}")
	private String html;

	@Override
	public Currency buildModel() {
		return ObjectMapperUtil.map(this, Currency.class);
	}

	@Override
	public Currency buildModel(Currency currency) {
		return ObjectMapperUtil.map(this, currency);
	}
}
