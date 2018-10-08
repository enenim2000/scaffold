package com.enenim.scaffold.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueueEvent {
    private String eventType;
    private String eventMethod;
    private Object eventService;
}
