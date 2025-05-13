package com.usbmanager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainWindow extends JFrame {
    public MainWindow(boolean isAdmin) {
        setTitle("USB Manager");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField deviceField = new JTextField("/dev/sdb1");
        JButton mountBtn = new JButton("Mount USB");
        JButton unmountBtn = new JButton("Unmount USB");

        mountBtn.addActionListener(e -> {
            String device = deviceField.getText();

            // Run mount script
            USBUtils.executeScript("src/main/resources/mount.sh", device);
            DesktopNotifier.notify("USB Mounted: " + device);
            if (!isAdmin)
                EmailNotifier.send("admin@example.com", "USB Mounted", device);

            // Get the mount path using lsblk
            String mountPath = getMountPath(device);

            if (mountPath != null) {
                System.out.println("Found mount path: " + mountPath);
                DesktopNotifier.notify("USB Scan Started");

                // Create a scanning dialog
                JDialog progressDialog = new JDialog(this, "Scanning USB...", true);
                JLabel label = new JLabel("Scanning for viruses, please wait...");
                JProgressBar progressBar = new JProgressBar();
                progressBar.setIndeterminate(true);
                progressDialog.setLayout(new BoxLayout(progressDialog.getContentPane(), BoxLayout.Y_AXIS));
                progressDialog.add(label);
                progressDialog.add(progressBar);
                progressDialog.setSize(300, 100);
                progressDialog.setLocationRelativeTo(this);

                // Perform 
			SwingWorker<String, Void> scannerWorker = new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() {
                        return VirusScanner.scanUSB(mountPath);
                    }

                    @Override
                    protected void done() {
                        progressDialog.dispose(); // Close dialog
                        try {
                            String result = get();
                            JOptionPane.showMessageDialog(null, "Virus Scan Result:\n" + result);
                            DesktopNotifier.notify("USB Scan Complete");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error during virus scan.");
                        }
                    }
                };

                scannerWorker.execute();
                progressDialog.setVisible(true); // Show dialog while scanning
            } else {
                JOptionPane.showMessageDialog(null, "Unable to determine the mount path.");
            }
        });

        unmountBtn.addActionListener(e -> {
            String device = deviceField.getText();
            USBUtils.executeScript("src/main/resources/unmount.sh", device);
            DesktopNotifier.notify("USB Unmounted: " + device);
            if (!isAdmin)
                EmailNotifier.send("admin@example.com", "USB Unmounted", device);
        });

        setLayout(new GridLayout(3, 1));
        add(deviceField);
        add(mountBtn);
        add(unmountBtn);
        setVisible(true);
    }

    private String getMountPath(String device) {
        try {
            ProcessBuilder pb = new ProcessBuilder("lsblk", "-o", "MOUNTPOINT", "-n", device);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String mountPath = reader.readLine();
            if (mountPath != null && !mountPath.trim().isEmpty()) {
                return mountPath.trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
