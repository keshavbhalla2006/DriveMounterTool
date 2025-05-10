package com.mountify;

import com.mountify.controller.MountifyController;

public class App {
    public static void main(String[] args) {
        System.out.println("[Mountify] Starting automated USB mount system...");
        MountifyController controller = new MountifyController();
        controller.automateMounting();
        System.out.println("[Mountify] Execution complete.");
    }
}
