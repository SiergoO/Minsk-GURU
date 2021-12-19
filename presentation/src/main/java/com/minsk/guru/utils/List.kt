package com.minsk.guru.utils

import com.minsk.guru.domain.model.Place


fun List<Place>.differenceWith(list: List<Place>): List<Place> =
    if (this.size > list.size) {
        this.asSequence().filter { f ->
            list.none { s -> s.id == f.id }
        }.toList()
    } else {
        list.asSequence().filter { f ->
            this.none { s -> s.id == f.id }
        }.toList()
    }
