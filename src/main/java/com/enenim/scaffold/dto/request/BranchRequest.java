package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.model.dao.Branch;
import com.enenim.scaffold.util.ObjectMapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class BranchRequest extends RequestBody<Branch>{

	@NotBlank(message = "@{branch.sol.required")
	private String sol;

	@NotBlank(message = "@{branch.name.required")
	private String name;

	@NotBlank(message = "@{branch.address.required")
	private String address;

	@Override
	public Branch buildModel() {
		return ObjectMapperUtil.map(this, Branch.class);
	}

	@Override
	public Branch buildModel(Branch branch) {
		return ObjectMapperUtil.map(this, branch);
	}
}
