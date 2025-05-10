package com.mountify;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private boolean runShellScript(String scriptName, String... args) {
        try {
            String scriptPath = "../scripts/" + scriptName;

            ProcessBuilder pb = new ProcessBuilder();
            pb.command(scriptPath);
            for (String arg : args) {
                pb.command().add(arg);
            }

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("\n--- Output from " + scriptName + " ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("--- Exit Code: " + exitCode + " ---");
            return (exitCode == 0);
        } catch (Exception e) {
            System.err.println("[ERROR] " + scriptName + " failed: " + e.getMessage());
            return false;
        }
    }

    @Test
    public void testFullUSBOperationFlow() {
        System.out.println("========== Mountify Full USB Automation Test ==========");

        boolean detectAndMount = runShellScript("auto_mount_usb.sh","/dev/sdc1");
        assertTrue(detectAndMount, "Mounting failed");

        boolean logEvent = runShellScript("auto_log_usb_event.sh","/dev/sdc1");
        assertTrue(logEvent, "Logging failed");

        boolean popupNotify = runShellScript("notify_popup.sh", "/dev/sdc1", "/mnt/usb"); // adjust device if needed
        assertTrue(popupNotify, "Popup notification failed");

        boolean emailNotify = runShellScript("send_email.sh", "/dev/sdc1", "/mnt/usb", "MOUNTED"); // adjust device if needed
        assertTrue(emailNotify, "Email notification failed");

        boolean unmount = runShellScript("unmount_usb.sh","sdc1");
        assertTrue(unmount, "Unmounting failed");

        System.out.println("========== ✅ All Mountify operations completed successfully ==========");
    }
}
