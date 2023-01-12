package com.spotrum.messagebroker.services;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.TokenUtil;
import com.eatthepath.pushy.apns.util.concurrent.PushNotificationFuture;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    public void pushMessage(String applicationToken, String title, String body){
        final SimpleApnsPushNotification pushNotification;

        final ApnsPayloadBuilder payloadBuilder = new SimpleApnsPayloadBuilder();
        payloadBuilder
                .setSound("default")
                .setBadgeNumber(1)
                .setAlertTitle(title)
                .setAlertBody(body);

        final String payload = payloadBuilder.build();
        final String token = TokenUtil.sanitizeTokenString(applicationToken);

        pushNotification = new SimpleApnsPushNotification(token, "com.spotrum.Spotrum", payload);
        try {
            final ApnsClient apnsClient = new ApnsClientBuilder()
                    .setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
                    .setClientCredentials(new File("pushCer.p12"), "CBRY875bv45zb2012!")
                    .build();

            final PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>>
                    sendNotificationFuture = apnsClient.sendNotification(pushNotification);

            final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                    sendNotificationFuture.get();

            if (pushNotificationResponse.isAccepted()) {
                System.out.println("Push notification accepted by APNs gateway.");
            } else {
                System.out.println("Notification rejected by the APNs gateway: " +
                        pushNotificationResponse.getRejectionReason());

                pushNotificationResponse.getTokenInvalidationTimestamp().ifPresent(timestamp -> {
                    System.out.println("\t…and the token is invalid as of " + timestamp);
                });
            }
        } catch (final ExecutionException e) {
            System.err.println("Failed to send push notification.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SSLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
