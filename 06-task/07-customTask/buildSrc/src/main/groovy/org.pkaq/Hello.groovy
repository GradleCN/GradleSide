import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class Hello extends DefaultTask {
    String words = 'hello'

    @TaskAction
    def action1() { println words + name }

    @TaskAction
    def action2() { println words + ' Tom'  }
}