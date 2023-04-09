package com.vadymhalaziuk.istesttask.utils

infix fun <A, B, C> Pair<A, B>.toTriple(param: C): Triple<A, B, C> =
    Triple(
        first, second, param
    )