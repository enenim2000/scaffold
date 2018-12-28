package com.enenim.scaffold.shared;

import lombok.Data;

@Data
public class Toggle {
    private String oldStatus;
    private String newStatus;
    private boolean skipAudit;
    private boolean skipAuthorization;
}