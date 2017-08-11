package org.pkaq

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@SpringBootApplication
@Controller
open class Booter:CommandLineRunner{
    @Throws(Exception::class)
    override fun run(vararg args: String) {
        println("  --- --- --- [ web started ] --- --- ---  ")
    }

    @RequestMapping("/")
    fun index():String {
        return "index"
    }
}

fun main(args: Array<String>) {
    org.springframework.boot.SpringApplication.run(org.pkaq.Booter::class.java, *args)
}