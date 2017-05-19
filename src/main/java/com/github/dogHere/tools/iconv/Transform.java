package com.github.dogHere.tools.iconv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dog on 5/19/17.
 */
public class Transform {

    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;
    private int bufferSize = 1024;
    private Action action ;

    public void transform() throws IOException {
        try {
                byte[] buffer = new byte[this.bufferSize];
                int size = 0;
                while ((size = inputStream.read(buffer)) != -1) {
                    action.act(buffer, 0, size);
                }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        }
    }

    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    public Transform setInputStream(InputStream inputStream) {
        this.inputStream = new BufferedInputStream(inputStream);
        return this;
    }

    public BufferedOutputStream getOutputStream() {
        return outputStream;
    }

    public Transform setOutputStream(OutputStream outputStream) {
        this.outputStream = new BufferedOutputStream(outputStream);
        return this;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public Transform setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public Transform setAction(Action action) {
        this.action=action;
        action.setOutputStream(this.outputStream);
        return this;
    }
}
