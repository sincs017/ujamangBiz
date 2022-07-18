package com.ujamang.biz.ui.project;

public class ProjectItem {

    private String project_category;
    private String project_name;

    public ProjectItem(String project_category, String project_name) {
        this.project_category = project_category;
        this.project_name = project_name;
    }

    public String getProject_category() {
        return project_category;
    }

    public void setProject_category(String project_category) {
        this.project_category = project_category;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
