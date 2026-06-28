package cn.hniu.utils;

import java.io.File;

public class Output implements Runnable{
    File get;

    public Output(File get) {
        this.get = get;
    }

    @Override
    public void run() {
        FileUtils.writeZip(get);
    }
}
