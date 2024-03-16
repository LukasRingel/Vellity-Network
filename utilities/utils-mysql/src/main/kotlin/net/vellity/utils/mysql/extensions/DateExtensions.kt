package net.vellity.utils.mysql.extensions

import java.time.Clock

fun nowUtc() = Clock.systemUTC().instant()