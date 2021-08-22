package com.manning.application.notification.templates.services;

import com.manning.application.notification.templates.model.NotificationParameter;
import com.manning.application.notification.templates.model.NotificationTemplateFormatterReq;
import com.manning.application.notification.templates.model.NotificationTemplateFormatterRes;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateFormatterService {
    private final SpringTemplateEngine templateEngine;

    public static String getBalanceSMSTemplate() {
        return "Hello ${name}"
                .concat("\n")
                .concat("Welcome to the Citizen Bank\n")
                .concat("Your balance is ${balance}\n")
                .concat("Thanks");
    }

    private static String getPhoneNumberChanged() {
        return "Hello ${name}"
                .concat("\n")
                .concat("Welcome to the Citizen Bank\n");
    }


    private Map<String, Object> paramsToMap(NotificationTemplateFormatterReq notificationTemplateFormatterReq) {
        System.out.println(notificationTemplateFormatterReq.getNotificationParameters().size());
        return notificationTemplateFormatterReq.getNotificationParameters().stream()
                .collect(Collectors
                        .toMap(NotificationParameter::getNotificationParameterName,
                                NotificationParameter::getNotificationParameterValue));
    }

    public NotificationTemplateFormatterRes getNotificationTemplateFormatterRes(
            NotificationTemplateFormatterReq notificationTemplateFormatterReq) {
        NotificationTemplateFormatterRes notificationTemplateRes = new NotificationTemplateFormatterRes();
        Map<String, Object> notificationParametersMap = paramsToMap(notificationTemplateFormatterReq);

        if (notificationTemplateFormatterReq.getNotificationMode().equals("EMAIL")) {

            File emailTemplateFile = new File("./src/main/resources/templates/email/" +
                    notificationTemplateFormatterReq.getNotificationTemplateName() + ".html");

            if (emailTemplateFile.exists()) {
                Context context = new Context();
                context.setVariables(notificationParametersMap);
                notificationTemplateRes.setEmailContent(
                        templateEngine.process(
                                "./email/" + notificationTemplateFormatterReq.getNotificationTemplateName() + ".html",
                                context));
                notificationTemplateRes.setStatus("SUCCESS");
                notificationTemplateRes.setStatusDescription(
                        "Successfully merged the template with the template parameters");
                notificationTemplateRes.setEmailSubject("Your Bank Balance");
            } else {
                notificationTemplateRes.setStatus("ERROR");
                notificationTemplateRes.setStatusDescription("Email Template is not available");
            }
        } else {
            String smsTemplate = null;
            if (notificationTemplateFormatterReq.getNotificationTemplateName().equalsIgnoreCase("ViewBalance")) {
                smsTemplate = getBalanceSMSTemplate();
            } else if (notificationTemplateFormatterReq.getNotificationTemplateName()
                    .equalsIgnoreCase("PhoneNumberChanged")) {
                smsTemplate = getPhoneNumberChanged();
            } else {
                notificationTemplateRes.setStatus("ERROR");
                notificationTemplateRes.setStatusDescription("Email Template is not available");
            }
            StringSubstitutor sub = new StringSubstitutor(notificationParametersMap);
            String notificationContent = sub.replace(smsTemplate);
            notificationTemplateRes.setStatus("SUCCESS");
            notificationTemplateRes.setStatusDescription(
                    "Successfully merged the template with the template parameters");
            notificationTemplateRes.setEmailSubject("Your Bank Balance");
            notificationTemplateRes.setSmsContent(notificationContent);
        }
        return notificationTemplateRes;
    }
}