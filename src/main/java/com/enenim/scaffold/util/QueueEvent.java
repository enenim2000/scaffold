package com.enenim.scaffold.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QueueEvent {
    private String eventType;
    private String eventMethod;
    private Object eventService;
}
