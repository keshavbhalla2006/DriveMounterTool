package com.mountify.controller;

import com.mountify.service.MountifyService;

public class MountifyController {

    private MountifyService service;

    public MountifyController() {
        service = new MountifyService();
    }

    public void performAllOperations() {
        service.mountAndLogUSB();
        service.sendEmailNotification();
        service.unmountUSB();
    }
}
