package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.AccountProvider;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AccountProviderRequest extends RequestBody<AccountProvider>{

	@NotBlank(message = "@{accountprovider.name.required}")
	private String name;

	@NotBlank(message = "@{accountprovider.code.required}")
	private String code;

	@Override
	public AccountProvider buildModel() {
		return ObjectMapperUtil.map(this, AccountProvider.class);
	}

	@Override
	public AccountProvider buildModel(AccountProvider accountProvider) {
		return ObjectMapperUtil.map(this, accountProvider);
	}
}
