package performance.ui;

import java.util.concurrent.ThreadFactory;


public class GroupThreadFactory implements ThreadFactory {
    private int threadId;
    private String name;
 
    public GroupThreadFactory(String name) {
        threadId = 1;
        this.name = name;
    }
 
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
