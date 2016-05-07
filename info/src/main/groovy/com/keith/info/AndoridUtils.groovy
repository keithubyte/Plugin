package com.keith.info

import groovy.xml.Namespace
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.invocation.Gradle

import java.util.regex.Matcher
import java.util.regex.Pattern

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

    public static String getGitBranch() {
        return "git rev-parse --abbrev-ref HEAD".execute().text.trim()
    }

    public static String getCommitId() {
        return "git rev-parse HEAD".execute().text.trim()
    }

    public static boolean isChangesNotCommit() {
        def output = "git status --porcelain".execute().text.readLines()
        println "commit lines ${output.size()}"
        if (output.isEmpty()) return false
        return !(output.size() == 1 && output.get(0).contains('appinfo.xml'))
    }

    public static boolean isSystemApp(Project project) {
        return "android.uid.system".equals(getUserId(project))
    }

    public static String getUserId(Project project) {
        def manifestFile = file(project.projectDir.absolutePath + '/src/main/AndroidManifest.xml')
        // 需要 import groovy.xml.Namespace
        def ns = new Namespace("http://schemas.android.com/apk/res/android", "android")
        def xml = new XmlParser().parse(manifestFile)
        return xml.attributes()[ns.sharedUserId].toString()
    }

    public static String getChannelId() {
        def matcher = releaseMatcher()
        def channelId = matcher.find() ? matcher.group(1).toLowerCase() : null
        if (channelId != null) {
            return channelId.trim().length() == 0 ? null : channelId
        }
        return null
    }

    public static boolean isRelease(Gradle gradle) {
        return getReleaseMatcher(gradle).find()
    }

    public static Matcher getReleaseMatcher(Gradle gradle) {
        def source = gradle.startParameter.taskRequests.first().args.first().toString()
        def pattern = Pattern.compile("assemble(.*?)(Release)");
        def matcher = pattern.matcher(source);
        return matcher
    }

    public static List<Dependency> getLinkinDeps() {
        def result = "gradlew.bat --daemon app:dependencies --configuration compile".execute().text.toString()
        def lines = result.readLines()
                .findAll { it.contains("com.linkin") }
                .collect { it.substring(it.indexOf('com.linkin')).split(' ')[0]}

        def map = new HashMap()
        for (String line : lines) {
            String[] array = line.split(':')
            if (array.size() == 3) {
                map.put(array[0], array[1] + ':' + array[2])
            }
        }

        def dependencies = new ArrayList()
        for (String key : map.keySet()) {
            dependencies.add("${key}:${map.get(key)}")
        }

        return dependencies
    }
}