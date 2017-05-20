package com.github.dogHere.tools.iconv;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by dog on 5/18/17.
 */
public class Recursive {

    private File root;
    private Things things;

    public void recursive()throws Exception{
        if(root.isDirectory()) recursive(root);
        else things.doThing(root);
    }

    private void recursive(File file) throws Exception {
        if(file.isDirectory()){
            for (File sFile :file.listFiles()){
                recursive(sFile);
            }
        }else {
            things.doThing(file);
        }
    }

    public File getRoot() {
        return root;
    }

    public Recursive setRoot(File root) {
        this.root = root;
        return this;
    }

    public Recursive setRoot(String rootPath) {
        this.root = new File(rootPath);
        return this;
    }


    public Things getThings() {
        return things;
    }

    public Recursive setThings(Things things) {
        this.things = things;
        return this;
    }
}
