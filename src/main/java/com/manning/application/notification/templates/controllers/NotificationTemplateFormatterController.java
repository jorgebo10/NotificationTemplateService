package com.manning.application.notification.templates.controllers;

import com.manning.application.notification.templates.model.NotificationTemplateFormatterReq;
import com.manning.application.notification.templates.model.NotificationTemplateFormatterRsp;
import com.manning.application.notification.templates.services.TemplateFormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notifications/templates")
@RequiredArgsConstructor
public class NotificationTemplateFormatterController {
    private final TemplateFormatterService templateFormatterService;

    @PostMapping
    public NotificationTemplateFormatterRsp create(@RequestBody @Valid NotificationTemplateFormatterReq notificationTemplateFormatterReq) {
        return templateFormatterService.getNotificationTemplateFormatterRes(notificationTemplateFormatterReq);
    }

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "UP";
    }
}
