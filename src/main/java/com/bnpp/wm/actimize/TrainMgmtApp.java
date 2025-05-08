package com.bnpp.wm.actimize;

import com.bnpp.wm.actimize.service.TrainService;

public class TrainMgmtApp {
    public static void main(String[] args) {
        startApplication();
    }

    public static void startApplication() {
        TrainService service = new TrainService();
        service.start();
    }
}
