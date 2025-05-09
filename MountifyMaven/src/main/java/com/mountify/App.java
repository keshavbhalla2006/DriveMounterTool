package com.mountify;

import com.mountify.service.MountifyService;

public class App {
    public static void main(String[] args) {
        MountifyService service = new MountifyService();

        System.out.println("Starting USB mount operation...");
        service.mountAndLogUSB();

        System.out.println("Sending email notification...");
        service.sendEmailNotification();

        System.out.println("Unmounting USB...");
        service.unmountUSB();

        System.out.println("All operations completed.");
    }
}
