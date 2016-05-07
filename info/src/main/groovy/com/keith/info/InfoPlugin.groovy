package com.keith.info

import org.gradle.api.Plugin
import org.gradle.api.Project

public class InfoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task('createAppInfo') << {
            println "package name = ${AndroidUtils.getPackageName(project)}"
            println "version name = ${AndroidUtils.getVersionName(project)}"
            println "version code = ${AndroidUtils.getVersionCode(project)}"
            println "min sdk = ${AndroidUtils.getMinSdk(project)}"
            println "git branch = ${AndroidUtils.getGitBranch()}"
            println "git commit id = ${AndroidUtils.getCommitId()}"
            println "is system app = ${AndroidUtils.isSystemApp(project)}"
        }
    }

}