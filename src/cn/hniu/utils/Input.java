package cn.hniu.utils;

import java.io.File;
import java.util.concurrent.Callable;


public class Input implements Callable<Integer> {
    File get;

    public Input(File get) {
        this.get = get;
    }

    @Override
    public Integer call() {
        return FileUtils.readZip(get);
    }
}
