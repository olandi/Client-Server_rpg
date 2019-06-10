package com.multiPlayer;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.multiPlayer.server.Server;

import org.slf4j.LoggerFactory;

public class RunServer {

    public static void main(String[] args) {

       /* LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);*/


        new Server().RunServer();


    }
}
