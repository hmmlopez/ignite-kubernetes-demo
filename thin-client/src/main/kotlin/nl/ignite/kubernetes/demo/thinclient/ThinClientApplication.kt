package nl.ignite.kubernetes.demo.thinclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThinClientApplication

fun main(args: Array<String>) {
    runApplication<ThinClientApplication>(*args)
}