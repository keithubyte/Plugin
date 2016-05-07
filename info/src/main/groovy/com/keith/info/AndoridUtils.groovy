package com.keith.info

import org.gradle.api.Project

class AndroidUtils {

    public static String getPackageName(Project project) {
        return String.valueOf(project.android.defaultConfig.applicationId)
    }

    public static String getVersionName(Project project) {
        return String.valueOf(project.android.defaultConfig.versionName)
    }

    public static String getVersionCode(Project project) {
        return String.valueOf(project.android.defaultConfig.versionCode)
    }

    public static String getMinSdk(Project project) {
        return String.valueOf(project.android.defaultConfig.minSdkVersion.mApiLevel)
    }

}