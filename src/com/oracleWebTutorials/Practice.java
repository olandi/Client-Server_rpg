package com.oracleWebTutorials;

import java.net.MalformedURLException;
import java.net.URL;

public class Practice {
    public static void main(String[] args)throws MalformedURLException {
        URL myURL = new URL("http://example.com/");
        URL gamelan = new URL("http", "example.com", 80, "pages/page1.html");
    }

}
