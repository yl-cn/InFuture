package com.future.task;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class FolderProcessor extends RecursiveTask<List<String>> {

    private static final long serialVersionUID = 2000L;

    private String path;

    private String extension;

    public FolderProcessor(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    protected List<String> compute() {
        List<String> list = new ArrayList<>();

        List<FolderProcessor> tasks = new ArrayList<>();

        File file = new File(path);
        File[] content = file.listFiles();

        if (content != null) {
            for (int i = 0; i < content.length; i++) {
                if (content[i].isDirectory()) {
                    FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);

                    task.fork();
                    tasks.add(task);
                } else {
                    if (checkFile(content[i].getName())) {
                        list.add(content[i].getAbsolutePath());
                    }
                }
            }

        }

        if (tasks.size() > 50) {
            log.info("{}: {} tasks ran.", file.getAbsolutePath(), tasks.size());
        }

        addResultsFromTasks(list, tasks);

        return list;
    }

    private void addResultsFromTasks(List<String> list,
                                     List<FolderProcessor> tasks) {
        for (FolderProcessor item : tasks) {
            list.addAll(item.join());
        }
    }


    private boolean checkFile(String name) {
        return name.endsWith(extension);
    }
}
