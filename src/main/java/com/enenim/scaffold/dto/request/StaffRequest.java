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

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class StaffRequest extends RequestBody<Staff>{

	@NotBlank
	@JsonProperty("employee_id")
	private String employeeId;

	@NotBlank
	@JsonProperty("active_hour_id")
	private Long activeHourId;

	@NotBlank
	@JsonProperty("fullname")
	private String fullName;

	@NotBlank
	private String email;

	@JsonProperty("holiday_login")
	private HolidayLogin holidayLogin;

	@JsonProperty("weekend_login")
	private WeekendLogin weekendLogin;

	@NotBlank
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
