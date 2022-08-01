package com.shahad.app.marvelapp.domain.mappers

interface Mapper<I,O> {
    fun map(input :I): O
}