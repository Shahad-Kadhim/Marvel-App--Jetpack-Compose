package com.shahad.app.core

interface Mapper<I,O> {
    fun map(input :I): O
}