package com.myDrafts.differentExamples;

import java.io.*;
import java.net.*;

public class HTTPConnection {
   private String strServer;
   private String strScript;
   private String strParams;

   public HTTPConnection(String strURL) {
     this(strURL, false);
   }

   public HTTPConnection(String strURL, boolean bIsTrim) {
     int 
       iiSlash = strURL.indexOf('/', "http://".length()) + 1;
       int iiParam = strURL.lastIndexOf('?') + 1;

     strServer = strURL.substring(0, iiSlash);
     strScript = strURL.substring(iiSlash, iiParam - 1);
     strParams = strURL.substring(iiParam);
   }

   public InputStream isOpenStream() throws UnknownHostException, IOException {
     // соединимся
     URLConnection conn = new URL(strServer + strScript).openConnection();
     conn.setDoOutput(true);
     conn.setUseCaches(false);
     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
     conn.setRequestProperty("Content-Length", "" + strParams.length());

     // посылаем данные
     OutputStream out = conn.getOutputStream();
     out.write(strParams.getBytes());
     out.flush();
     out.close();

     // получим поток с ответом
     return conn.getInputStream();
   }
} 