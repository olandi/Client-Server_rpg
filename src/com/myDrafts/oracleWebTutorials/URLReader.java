package com.myDrafts.oracleWebTutorials;

import java.net.*;
import java.io.*;

public class URLReader {
    public static void main(String[] args) throws Exception {

        URL oracle = new URL("https://mail.ru/");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
/*
You can use this URLConnection object to setup parameters and general request properties that you may need before connecting.
 Connection to the remote object represented by the URL is only initiated when the URLConnection.connect method is called.
 When you do this you are initializing a communication link between your Java program and the URL over the network.
  For example, the following code opens a connection to the site example.com:

try {
    URL myURL = new URL("http://example.com/");
    URLConnection myURLConnection = myURL.openConnection();
    myURLConnection.connect();
}
catch (MalformedURLException e) {
    // new URL() failed
    // ...
}
catch (IOException e) {
    // openConnection() failed
    // ...
}
 */