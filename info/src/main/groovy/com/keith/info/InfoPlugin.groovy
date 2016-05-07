package com.keith.info

import org.gradle.api.Plugin
import org.gradle.api.Project

public class InfoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task('createAppInfo') << {
            println "package name = ${AndroidUtils.getPackageName(project)}"
        }
    }

}