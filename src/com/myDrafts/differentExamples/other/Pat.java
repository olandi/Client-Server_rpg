package com.myDrafts.differentExamples.other;

public class Pat {

        String filePath = "./src/"+ this.getClass().getPackage().getName()//.replaceAll(".","/") ;
;



    public static void main(String[] args) {
        Pat pat = new Pat();
        System.out.println(pat.filePath);
    }
}
