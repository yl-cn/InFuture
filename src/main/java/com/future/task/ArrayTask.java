package com.future.task;

import java.util.concurrent.RecursiveAction;

public class ArrayTask extends RecursiveAction {

    // 2. 声明一个私有 int array 属性，名为 array，用来储存你要增加的 array 的元素。
    private int[] array;

    // 3. 声明2个私有 int 属性，名为 start 和 end，用来储存 此任务已经处理的元素块的头和尾的位置。
    private int start;
    private int end;

    // 4. 实现类的构造函数，初始化属性值。
    public ArrayTask(int array[], int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    // 5. 用任务的中心逻辑来实现 compute()
    // 方法。如果此任务已经处理了超过100任务，那么把元素集分成2部分，再创建2个任务分别来执行这些部分，使用 fork() 方法开始执行，并使用
    // join() 方法等待它的终结。
    protected void compute() {
        if (end - start > 100) {
            int mid = (start + end) / 2;
            ArrayTask task1 = new ArrayTask(array, start, mid);
            ArrayTask task2 = new ArrayTask(array, mid, end);

            task1.fork();
            task2.fork();

            task1.join();
            task2.join();

            // 6. 如果任务已经处理了100个元素或者更少，那么在每次操作之后让线程进入休眠5毫秒来增加元素。
        } else {
            for (int i = start; i < end; i++) {
                array[i]++;

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
