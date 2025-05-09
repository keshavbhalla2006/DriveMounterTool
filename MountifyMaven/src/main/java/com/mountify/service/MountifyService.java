package com.mountify.service;

import java.io.IOException;

public class MountifyService {

    private static final String SCRIPT_PATH = "../scripts/";

    public void mountAndLogUSB() {
        runScript("mount_usb.sh");
    }

    public void unmountUSB() {
        runScript("unmount_usb.sh");
    }

    public void sendEmailNotification() {
        runScript("notify_popup.sh"); // or replace with actual email script like "email_notify.sh"
    }

    private void runScript(String scriptName) {
        try {
            ProcessBuilder pb = new ProcessBuilder(SCRIPT_PATH + scriptName);
            pb.inheritIO();
            Process process = pb.start();
            int exitCode = process.waitFor();
            System.out.println(scriptName + " exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error running script: " + scriptName);
            e.printStackTrace();
        }
    }
}
