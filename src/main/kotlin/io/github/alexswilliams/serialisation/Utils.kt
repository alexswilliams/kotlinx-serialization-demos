package io.github.alexswilliams.serialisation

import java.net.URL

object ArbitraryVictimObject

fun classpath(path: String): URL =
    ArbitraryVictimObject::class.java.getResource(path) ?: throw Exception("Could not find $path")
