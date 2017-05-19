package com.github.dogHere.tools.iconv;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by dog on 5/19/17.
 */
public interface Action {
    public void act(byte [] buffer,int off,int len) throws IOException;
    public void setOutputStream(OutputStream outputStream);
}
