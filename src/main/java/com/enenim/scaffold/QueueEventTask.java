package com.enenim.scaffold;


import com.enenim.scaffold.interfaces.IQueueEventTask;
import lombok.Data;

@Data
public class QueueEventTask  implements IQueueEventTask {
    private int i;

    public QueueEventTask(int i){
        this.i = i;
    }

    @Override
    public void execute(){
        System.out.println("about to execute task" + i);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task executed" + i);
    }
}
