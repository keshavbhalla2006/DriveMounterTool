package com.mountify.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellExecutor {
    public static String executeShellScript(String scriptPath) {
        StringBuilder output = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            output.append("[INFO] Script exited with code: ").append(exitCode);
        } catch (Exception e) {
            output.append("[ERROR] ").append(e.getMessage());
        }

        return output.toString();
    }
}
