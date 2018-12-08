package org.pkaq
import org.gradle.api.Plugin
import org.gradle.api.Project

class FoLengPlugin implements Plugin<Project>{
	@Override		
    void apply(Project project) {
        /**注册一个扩展容器，扩展容器可以使我们在一个闭包中为task赋值*/
        project.extensions.create('foleng', FolengExtension)
        folengTask(project)
    }

    private void folengTask(Project project){
        project.tasks.withType(FoLengTask) {
            /**查找扩展容器中已配置的属性*/
            def extension = project.extensions.findByName('foleng')
            /**将闭包中的扩展属性值赋给 FoLengTask 的相关属性*/
            conventionMapping.words = { extension.words }
        }
        project.task('mlfl', type: FoLengTask) {
            conventionMapping.words = {
               project.hasProperty('words') ?
                       project.words :
                       project.extensions.findByName('foleng').words
            }
            doLast {
                println ' The End '
            }
        }
    }
}