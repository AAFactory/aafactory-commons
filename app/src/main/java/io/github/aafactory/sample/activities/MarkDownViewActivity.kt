package io.github.aafactory.sample.activities

import io.github.aafactory.commons.activities.BaseMarkDownViewActivity
import io.noties.prism4j.Prism4j
import io.noties.prism4j.annotations.PrismBundle

@PrismBundle(include = ["java", "kotlin", "javascript"], grammarLocatorClassName = ".GrammarLocatorSourceCode")
open class MarkDownViewActivity : BaseMarkDownViewActivity() {
    init {
        mPrism4j = Prism4j(GrammarLocatorSourceCode())
    }
}
