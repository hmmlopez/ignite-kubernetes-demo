package nl.ignite.kubernetes.demo.server.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ServerController {
    @GetMapping("/")
    fun root(): String {
        return "This is the server controller"
    }
}