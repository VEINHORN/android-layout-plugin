package com.veinhorn.android.yaml.plugin

// import com.veinhorn.android.yaml.YamlTransformator
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.resources.TextResourceFactory

/**
 * Created by veinhorn on 13.2.17.
 */
public class YamlViewPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task('genviews') {

            doLast {
                Set<File> files = project.fileTree('src/main/res/yaml/').getFiles()
                for (file in files) {
                    println file.getAbsolutePath()
                }
            }
        }
    }
}
