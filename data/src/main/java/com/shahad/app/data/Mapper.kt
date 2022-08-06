package com.shahad.app.data

interface Mapper<I,O> {
    fun map(input :I): O
}