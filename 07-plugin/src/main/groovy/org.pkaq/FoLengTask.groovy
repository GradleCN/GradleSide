package org.pkaq

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class FoLengTask extends DefaultTask {
	@Input
    String words = 'Hello'

    FoLengTask(){
    	description = "Mai Le FoLeng?"
    	group = "FoLeng"
    }

    @TaskAction
    void mlfl() {
        // 获取输入的属性
        words = getWords()
        println '''
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\\  =  /O
               ____/`---'\\____
             .'  \\\\|     |//  `.
            /  \\\\|||  :  |||//  \\
           /  _||||| -:- |||||-  \\
           |   | \\\\\\  -  /// |   |
           | \\_|  ''\\---/''  |   |
           \\  .-\\__  `-`  ___/-. /
         ___`. .'  /--.--\\  `. . __
      ."" '<  `.___\\_<|>_/___.'  >'"".
     | | :  ` - `.;`\\ _ /`;.`/ - ` : | |
     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /
======`-.____`-.___\\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            佛祖保佑       永无BUG

            '''
        println "            < -   ${words}   ->"
    }
}