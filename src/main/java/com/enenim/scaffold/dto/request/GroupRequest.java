package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.HolidayLogin;
import com.enenim.scaffold.enums.WeekendLogin;
import com.enenim.scaffold.model.dao.ActiveHour;
import com.enenim.scaffold.model.dao.Group;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class GroupRequest extends RequestBody<Group>{

	private String name;

    @JsonProperty("weekend_login")
    private String weekendLogin;

    @JsonProperty("holiday_login")
    private String holidayLogin;

    @JsonProperty("active_hour_id")
    private String activeHourId;

	@Override
	public Group buildModel() {
		return buildModel(new Group());
	}

	@Override
	public Group buildModel(Group group) {
		group.setName(name);
		group.setCreatedAt(new Date());
		group.setEnabled(EnabledStatus.PENDING_ENABLED);
		group.setActiveHour(new ActiveHour(Long.parseLong(activeHourId)));
		group.setRole(group.getName().toUpperCase());

		if (weekendLogin.equalsIgnoreCase(WeekendLogin.YES.getValue().toString())) {
			group.setWeekendLogin(WeekendLogin.YES);
		} else if (weekendLogin.equalsIgnoreCase(WeekendLogin.NO.getValue().toString())) {
			group.setWeekendLogin(WeekendLogin.NO);
		}else {
			group.setWeekendLogin(null);
		}

		if (holidayLogin.equalsIgnoreCase(HolidayLogin.YES.getValue().toString())) {
			group.setHolidayLogin(HolidayLogin.YES);
		} else if (holidayLogin.equalsIgnoreCase(HolidayLogin.NO.getValue().toString())) {
			group.setHolidayLogin(HolidayLogin.NO);
		}else {
			group.setHolidayLogin(null);
		}

		return group;
	}
}
