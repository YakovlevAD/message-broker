package com.spotrum.messagebroker.services;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.Map;

@Service
public class NotificationService {

    public void pushMessage(String alertTitle, String alertBody, String certPath, String certPass, String token) {
        System.out.println(">>>>init push");
        ApnsService service = APNS.newService()
                .withCert(certPath, certPass)
                .withSandboxDestination()
                .build();

        String payload = APNS.newPayload()
                .alertBody(alertBody)
                .alertTitle(alertTitle).build();

        var note = service.push(token, payload);

        Map<String, Date> inactiveDevices = service.getInactiveDevices();
        for (String deviceToken : inactiveDevices.keySet()) {
            Date inactiveAsOf = inactiveDevices.get(deviceToken);
            System.out.println(">>>>Inaktive: "+deviceToken+" "+inactiveAsOf);
        }
        System.out.println(">>>>>Push sended "+note.toString());
    }
}
