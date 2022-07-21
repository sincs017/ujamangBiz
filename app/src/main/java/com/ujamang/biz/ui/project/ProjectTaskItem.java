package com.ujamang.biz.ui.project;

public class ProjectTaskItem {

    private String project_task_name;
    private String project_task_writer;
    private String project_task_write_date;

    public ProjectTaskItem(String project_task_name, String project_task_writer, String project_task_write_date) {
        this.project_task_name = project_task_name;
        this.project_task_writer = project_task_writer;
        this.project_task_write_date = project_task_write_date;
    }

    public String getProject_task_name() {
        return project_task_name;
    }

    public void setProject_task_name(String project_task_name) {
        this.project_task_name = project_task_name;
    }

    public String getProject_task_writer() {
        return project_task_writer;
    }

    public void setProject_task_writer(String project_task_writer) {
        this.project_task_writer = project_task_writer;
    }

    public String getProject_task_write_date() {
        return project_task_write_date;
    }

    public void setProject_task_write_date(String project_task_write_date) {
        this.project_task_write_date = project_task_write_date;
    }
}
