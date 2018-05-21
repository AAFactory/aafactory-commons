package io.github.aafactory.sample.adapters

import android.view.View
import io.github.aafactory.sample.models.Showcase
import kotlinx.android.synthetic.main.item_showcase.*

/**
 * Created by CHO HANJOONG on 2018-04-19.
 */

class ShowcaseViewHolder(containerView: View) : BaseViewHolder<Showcase>(containerView) {
    override fun bindData(data: Showcase) {
        title.text = data.getRepositoryNameWithSubName()
        description.text = data.description
        starsAll.text = "${data.stargazersCount}"
        forks.text = "${data.forksCount}"
        owner.text = "Built by ${data.owner}"
    }
}