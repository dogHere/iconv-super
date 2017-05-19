package com.github.dogHere.tools.iconv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dog on 5/19/17.
 */
public class Convert implements Action {
    private OutputStream outputStream;
    private String fromEncode ;
    private String toEncode;


    @Override
    public void act(byte[] buffer,int off,int len) throws IOException {

        outputStream.write(new String(buffer, off,len , fromEncode).getBytes(toEncode));
    }



    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    public String getFromEncode() {
        return fromEncode;
    }

    public Convert setFromEncode(String fromEncode) {
        this.fromEncode = fromEncode;
        return this;
    }

    public String getToEncode() {
        return toEncode;
    }

    public Convert setToEncode(String toEncode) {
        this.toEncode = toEncode;
        return this;
    }
}
