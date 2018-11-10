package com.enenim.scaffold.dto.request;

import com.enenim.scaffold.constant.ValidationConstant;
import com.enenim.scaffold.enums.EnabledStatus;
import com.enenim.scaffold.enums.HolidayLogin;
import com.enenim.scaffold.enums.WeekendLogin;
import com.enenim.scaffold.model.dao.ActiveHour;
import com.enenim.scaffold.model.dao.Group;
import com.enenim.scaffold.shared.ErrorValidator;
import com.enenim.scaffold.shared.Validation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
@ToString
public class GroupRequest extends RequestBody<Group>{

	public GroupRequest(){
		super();
		validateRequest();
	}

	@Transient
	public static final String MODEL_KEY = "group";

	@Transient
	public static final String MODEL_KEY_INVALID = MODEL_KEY + ".id.invalid";

	@Transient
	public static final String NAME = "name";

	@Transient
	public static final String WEEKEND_LOGIN = "weekend_login";

	@Transient
	public static final String HOLIDAY_LOGIN = "holiday_login";

	@Transient
	public static final String ACTIVE_HOUR_ID = "active_hour_id";

	private String name;

    @JsonProperty(WEEKEND_LOGIN)
    private String weekendLogin;

    @JsonProperty(HOLIDAY_LOGIN)
    private String holidayLogin;

    @JsonProperty(ACTIVE_HOUR_ID)
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

	@Override
	public Validation validateRequest(){
		ErrorValidator errorValidator = new ErrorValidator();
		new ErrorValidator()
				.addError(Validation.validateInput(getName(), MODEL_KEY, NAME,  ValidationConstant.TYPE_REQUIRED))
				.addError(Validation.validateInput(getActiveHourId(), MODEL_KEY, ACTIVE_HOUR_ID,  ValidationConstant.TYPE_REQUIRED))
				.addError(Validation.validateInput(getActiveHourId(), MODEL_KEY, ACTIVE_HOUR_ID, ValidationConstant.TYPE_NUMBER));
		return new Validation(errorValidator.build());
	}
}
