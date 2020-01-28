package com.jonikoone

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.gson.*
import io.ktor.features.*
import java.awt.*
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

class Message(
    val msg: String
)

val collection = Collections.synchronizedCollection(mutableListOf(
    Message("hello"),
    Message("world")
))

val robot = Robot()

val w = Toolkit.getDefaultToolkit().screenSize.width
val h = Toolkit.getDefaultToolkit().screenSize.height

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {

        }
    }

    routing {
       /* get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/json/myjson") {
            val parameters = call.receiveParameters()
            println(parameters["message"])
            call.respondText("message: ${parameters["message"]}", contentType = ContentType.Text.Plain)
//            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/collection") {
            call.respond(mapOf("messages" to synchronized(collection) { collection.toList()}))
        }*/

        get("/api") {
            val message = call.request.queryParameters
            println(message["message"])
//            val msg = message["msg"] as String
//            collection.add(Message(msg))
            call.respondText("OK")
        }

        post("/api") {
            val message = call.request.queryParameters
            println(message["message"])
//            val msg = message["msg"] as String
//            collection.add(Message(msg))
            call.respondText("OK")
        }

        post("/addnew") {

            call.respondRedirect("/collection")
        }

        post("/move") {
            var receive = call.receive<Offset>()
            robot.mouseMove(MouseInfo.getPointerInfo().location.x + receive.x.toInt(), MouseInfo.getPointerInfo().location.y + receive.y.toInt())
        }

        post ("/clickLMB") {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        }

        post ("/clickRMB") {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        }

        post ("/press") {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        }

        post ("/release") {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        }

        post ("/scrollwheel") {
            var receive = call.receive<Offset>()
            robot.mouseWheel(receive.y.toInt())
        }


    }
}

data class Offset(
    val x: Float,
    val y: Float
)



