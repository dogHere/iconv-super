package com.github.dogHere.tools.iconv;

import org.mozilla.intl.chardet.HtmlCharsetDetector;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by dog on 5/18/17.
 */
public class Main {

    public static void printHelp(){
        System.out.println("Usage: ./Iconv [OPTIONS] \n\t" +
                "-h print this help and exit.\n\t" +
                "-v view.\n\t"+
                "-c copy.\n\t"+
                "-n no convert.\n\t"+
                "-f from encode.\n\t-t to encode.\n\t" +
                "-r regex to match files\n\t" +
                "-s from dir.\n\t" +
                "-d to dir.\n");
        System.exit(0);
    }

    public static void main(String[]args) throws Exception {





        String fromEncode =null;
        String toEncode = "utf-8";
        String fromDir = ".";
        String toDir = "/tmp/";
        String regex =null;
        boolean view = false;
        boolean isCopy = false;
        boolean noConvert = false;



        int length = args.length;
        if(length==0) printHelp();

        try {
            for (int i = 0; i < length; i++) {

                if (args[i].equals("-h")) printHelp();
                if (args[i].equals("-v")) view = true;
                if (args[i].equals("-c")) isCopy = true;
                if (args[i].equals("-n")) noConvert = true;

                if (args[i].equals("-f") && i + 1 <= length) fromEncode = args[i + 1];
                if (args[i].equals("-t") && i + 1 <= length) toEncode = args[i + 1];
                if (args[i].equals("-r") && i + 1 <= length) regex = args[i + 1];


                if (args[i].equals("-s") && i + 1 <= length) fromDir = args[i + 1];
                if (args[i].equals("-d") && i + 1 <= length) toDir = args[i + 1];


            }

        }catch (Exception e){
            e.printStackTrace();
            printHelp();
        }
        File fromDirFile = new File(fromDir);
        File toDirFile = new File(toDir);
        if(!fromDirFile.isDirectory())throw new RuntimeException(fromDir +" is not a dir.");
        if(toDirFile.exists()){
            System.out.println("!!!!!!!!!!!!!!!!!!\tWARNING:"+toDir+" exists\t!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("OverWrite "+toDir+" and go on:Yes/no?");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line =reader.readLine().trim();
            if(!line.equals("Yes")) System.exit(0);
        }


        if(fromEncode==null) printHelp();
        if(toEncode==null) printHelp();
        if(fromDir==null) printHelp();
        if(toDir==null) printHelp();


        doIt(fromDirFile,fromEncode,toEncode,regex,new File(toDir),view,isCopy);
    }

    public static void doIt(File fromDir,String fromEncode,String toEncode,String regex,File toDir,boolean view,boolean isCopy
                            ) throws Exception {

        new Recursive()
                .setRoot(fromDir)
                .setThings(new Things() {
                    @Override
                    public void doThing(File file) throws Exception {

                        File newName =new File(file.toString().replaceFirst(fromDir.toString(),toDir.toString()));
                        if(!newName.getParentFile().exists())newName.getParentFile().mkdirs();

                        boolean found = false;
                        String fromEncode_inner = null;
                        if(null!=regex) {
                            //
                            Pattern p = Pattern.compile(regex);
                            Matcher m = p.matcher(file.getName());
                            if(m.find())  found = true;
                        }else {
                            ////////////////////check file type////////////////////
                            String fileEncoded = checkFileType(file);
                            ////////////////////////////////////////////////////////////////////////

                            if (fileEncoded != null&&fileEncoded.equals(fromEncode)) {
                                found = true;
                            } else {
                                found = false;
                            }
                        }

                        if(found){
                            new Transform()
                                    .setInputStream(new FileInputStream(file))
                                    .setOutputStream(new FileOutputStream(newName))
                                    .setAction(new Convert().setFromEncode(fromEncode).setToEncode(toEncode))
                                    .transform();

                            if(view) {
                                System.out.println("Convert file "+file +" from code "+fromEncode+" to code "+toEncode+" to file "+newName);
                            }


                        }else {
                            if(!isCopy) return;
                            new Transform()
                                    .setInputStream(new FileInputStream(file))
                                    .setOutputStream(new FileOutputStream(newName))
                                    .setAction(new Copy())
                                    .transform();
                            if(view) {
                                System.out.println("Copy file "+file+" to file "+newName);
                            }
                        }
                    }
                })
                .recursive();

    }

    private static String checkFileType(File file) throws IOException {
        byte[] buf = new byte[1024] ;
        int len;
        boolean done = false ;
        boolean isAscii = true ;

        nsDetector det = new nsDetector(nsPSMDetector.ALL) ;

        // Set an observer...
        // The Notify() will be called when a matching charset is found.

        det.Init(new nsICharsetDetectionObserver() {
            public void Notify(String charset) {
                HtmlCharsetDetector.found = true ;
//                                System.out.println("CHARSET = " + charset);
            }
        });


        BufferedInputStream imp  =null;
        try {
            imp = new BufferedInputStream(new FileInputStream(file));
            String[] pe = null;
            if ((len = imp.read(buf, 0, buf.length)) != -1) {

                // Check if the stream is only ascii.
                if (isAscii)
                    isAscii = det.isAscii(buf, len);
                // DoIt if non-ascii and not done yet.
                if (!isAscii && !done)
                    done = det.DoIt(buf, len, false);
                pe = det.getProbableCharsets();
                if (!isAscii) Arrays.sort(pe);
//            if(!isAscii) System.out.println(Arrays.toString(pe) +"\t"+ file);
            }
            det.DataEnd();

            if (!isAscii) {
                return pe[0].toLowerCase();
            } else {
                return null;
            }
        }finally {
            imp.close();
        }
    }
}