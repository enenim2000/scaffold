package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.PaymentChannel;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class PaymentChannelRequest extends RequestBody<PaymentChannel>{

	@NotBlank(message = "@{paymentchannel.name.required}")
	private String name;

	@NotBlank(message = "@{paymentchannel.code.required}")
	private String code;

	@NotBlank(message = "@{paymentchannel.txn_prefix.required}")
	@Size(min = 3, max = 3, message = "@{paymentchannel.txn_prefix.size}")
	@JsonProperty("txn_prefix")
	private String txnPrefix;

	@NotBlank(message = "@{paymentchannel.notification_medium.required}")
	@JsonProperty("notification_medium")
	private String notificationMedium;

	@Override
	public PaymentChannel buildModel() {
		return ObjectMapperUtil.map(this, PaymentChannel.class);
	}

	@Override
	public PaymentChannel buildModel(PaymentChannel paymentChannel) {
		return ObjectMapperUtil.map(this, paymentChannel);
	}
}
