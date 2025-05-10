package com.mountify.service;

import com.mountify.utils.ShellExecutor;

public class MountifyService {
    private final String SCRIPT_PATH = "../scripts/auto_mount_usb.sh";

    public void mountAndLogUSB() {
        System.out.println("[INFO] Starting automation script...");
        String output = ShellExecutor.executeShellScript(SCRIPT_PATH);
        System.out.println("[OUTPUT] " + output);
    }
}
