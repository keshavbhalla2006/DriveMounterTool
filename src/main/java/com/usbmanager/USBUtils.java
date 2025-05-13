package com.usbmanager;

import java.io.IOException;

public class USBUtils {
    public static void executeScript(String scriptPath, String device) {
        try {
            new ProcessBuilder("/bin/bash", scriptPath, device).inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
