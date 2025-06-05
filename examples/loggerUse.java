package com.example;

import net.Mirik9724.api.*

public class Main {
    public static void main(String[] args) {
        LogInit("MyApp");

        logger_.info("Logger is working!");
        logger_.warn("test warn");
        logger_.error("ER: 404");
    }
}
