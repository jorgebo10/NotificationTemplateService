package com.manning.application.notification.templates.controllers;

import com.manning.application.notification.templates.model.NotificationParameter;
import com.manning.application.notification.templates.model.NotificationTemplateFormatterReq;
import com.manning.application.notification.templates.model.NotificationTemplateFormatterRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notification/templates")
@RequiredArgsConstructor
public class NotificationTemplateFormatterController {
    private final TemplateFormatterService templateFormatterService;

    @PostMapping
    public NotificationTemplateFormatterRes create(@RequestBody @Valid NotificationTemplateFormatterReq notificationTemplateFormatterReq) {
        return templateFormatterService.getNotificationTemplateFormatterRes(notificationTemplateFormatterReq);
    }
}
