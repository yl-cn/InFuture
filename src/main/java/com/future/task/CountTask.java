package com.future.task;

import com.google.common.base.Stopwatch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
public class CountTask extends RecursiveTask<Integer>{

    private static final long serialVersionUID = 1000L;

    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    @Override
    protected Integer compute() {
        int sum = 0;

        Stopwatch stopwatch = Stopwatch.createStarted();

        //如果任务足够小就计算任务
        if((end-start) <= THRESHOLD) {
            for(int i = start; i <= end; i++) {
                sum += i;
            }
        }
        else {
            //如果任务大于阀值，就分裂成两个子任务计算

            int middle = (start+end) / 2;
            CountTask leftTask =new CountTask(start, middle);
            CountTask rightTask =new CountTask(middle + 1, end);


            leftTask.fork();
            rightTask.fork();

            if (leftTask.isCompletedAbnormally()) {
                log.error("Main: An leftTask exception has ocurred");
                log.error("Main: leftTask  {}",leftTask.getException());
            }

            if (rightTask.isCompletedAbnormally()) {
                log.error("Main: An rightTask exception has ocurred");
                log.error("Main: rightTask  {}",rightTask.getException());
            }

            //合并子任务
            sum = leftTask.join() + rightTask.join();

        }

        log.info("耗时: {} ms ", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return sum;
    }


}
