package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.enums.HolidayLogin;
import com.enenim.scaffold.enums.WeekendLogin;
import com.enenim.scaffold.model.dao.ActiveHour;
import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.model.dao.Staff;
import com.enenim.scaffold.util.ObjectMapperUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class StaffRequest extends RequestBody<Staff>{

	@NotBlank(message = "@{staff.employee_id.required")
	@JsonProperty("employee_id")
	private String employeeId;

	@NotBlank(message = "@{staff.active_hour_id.required")
	@JsonProperty("active_hour_id")
	private Long activeHourId;

	@NotBlank(message = "@{staff.fullname.required")
	@JsonProperty("fullname")
	private String fullName;

	@NotBlank(message = "@{staff.email.required")
	@Email(message = "@{staff.email.pattern}")
	private String email;

	@JsonProperty("holiday_login")
	private HolidayLogin holidayLogin;

	@JsonProperty("weekend_login")
	private WeekendLogin weekendLogin;

	@NotBlank(message = "@{staff.group_id.required")
	@JsonProperty("group_id")
	private Long groupId;

	@Override
	public Staff buildModel() {
		Staff staff = ObjectMapperUtil.map(this, Staff.class);
		staff.setGroup(new Group(this.getGroupId()));
		staff.setActiveHour(new ActiveHour(this.getActiveHourId()));
		return staff;
	}

	@Override
	public Staff buildModel(Staff staff) {
		staff.setGroup(new Group(this.getGroupId()));
		staff.setActiveHour(new ActiveHour(this.getActiveHourId()));
		return ObjectMapperUtil.map(this, staff);
	}
}
