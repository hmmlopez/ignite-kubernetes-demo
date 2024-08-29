package nl.ignite.kubernetes.demo.client.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientController {

    @GetMapping("/")
    fun root(): String {
        return "This is the client controller"
    }

}