package io.github.aafactory.sample.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by CHO HANJOONG on 2018-04-20.
 */

open class Showcase : RealmObject {
    @PrimaryKey
    var sequence: Int = 0

    var owner: String = ""
    var name: String = ""
    var isCheatSheet: Boolean = false
    var description: String = ""
    var cheatSheetUrl: String = ""
    var forceAppendCodeBlock: Boolean = false
    var stargazersCount: Int = 0
    var forksCount: Int = 0

    constructor()

    constructor(owner: String, name: String, isCheatSheet: Boolean, description: String, cheatSheetUrl: String = "", forceAppendCodeBlock: Boolean = false, stargazersCount: Int = 0, forksCount: Int = 0) {
        this.owner = owner
        this.name = name
        this.isCheatSheet = isCheatSheet
        this.description = description
        this.cheatSheetUrl = cheatSheetUrl
        this.forceAppendCodeBlock = forceAppendCodeBlock
        this.stargazersCount = stargazersCount
        this.forksCount = forksCount
    }
}