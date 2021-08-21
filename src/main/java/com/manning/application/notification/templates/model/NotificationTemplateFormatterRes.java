package com.manning.application.notification.templates.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class NotificationTemplateFormatterRes {
    private String status;
    private String statusDescription;
    private String emailContent;
    private String smsContent;
    private String emailSubject;
}
