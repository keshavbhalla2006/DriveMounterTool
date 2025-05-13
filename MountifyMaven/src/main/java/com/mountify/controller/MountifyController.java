package com.mountify.controller;

import com.mountify.service.MountifyService;

public class MountifyController {
    private MountifyService service;

    public MountifyController() {
        this.service = new MountifyService();
    }

    public void automateMounting() {
        service.mountAndLogUSB();
    }
}
