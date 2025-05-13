package com.usbmanager;

import java.io.IOException;

public class DesktopNotifier {
    public static void notify(String message) {
        try {
            new ProcessBuilder("notify-send", message).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
