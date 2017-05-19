package com.github.dogHere.tools.iconv;

import java.io.*;

/**
 * Created by dog on 5/19/17.
 */
public class Copy implements Action{

    private OutputStream outputStream;
    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    @Override
    public void act(byte[] buffer,int off,int len) throws IOException {
        outputStream.write(buffer,off,len);
    }


}
