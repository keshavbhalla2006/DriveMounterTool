package com.usbmanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class VirusScanner {

    public static String scanUSB(String mountPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("clamscan", "-r", mountPath);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder fullOutput = new StringBuilder();
            Queue<String> lastLines = new LinkedList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                fullOutput.append(line).append("\n");

                // Keep last 10 lines
                if (lastLines.size() == 10) lastLines.poll();
                lastLines.offer(line);
            }

            int exitCode = process.waitFor();

            System.out.println("=== Full clamscan output ===\n" + fullOutput); // log full output to terminal

            StringBuilder summary = new StringBuilder();
            summary.append(exitCode == 0 ? "✅ No threats found.\n\n" : "⚠️ Threats may have been found!\n\n");
            summary.append("🔍 Last 10 lines of scan:\n");
            for (String l : lastLines) summary.append(l).append("\n");

            return summary.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error during scan: " + e.getMessage();
        }
    }
}
