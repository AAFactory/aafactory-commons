package io.github.aafactory.sample.adapters

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.aafactory.sample.databinding.ItemContributorBinding

class ContributorAdapter(private val activity: Activity, private val contributors: List<Contributor>) : RecyclerView.Adapter<ContributorAdapter.ContributorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(ItemContributorBinding.inflate(activity.layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bindTo(contributors[position])
    }

    override fun getItemCount(): Int = contributors.size

    inner class ContributorViewHolder(private val itemContributorBinding: ItemContributorBinding) : RecyclerView.ViewHolder(itemContributorBinding.root) {
        fun bindTo(contributor: Contributor) {
            itemContributorBinding.run {
                textName.text = contributor.user.name ?: "N/A"
                textLocation.text = contributor.user.location ?: "N/A"
                textLoginId.text = contributor.login
                textBlog.text = if (contributor.user.blog?.isNotEmpty() == true) contributor.user.blog else "N/A"
                textContributions.text = contributor.contributions.toString()
                Glide.with(activity).load(contributor.user.avatar_url).circleCrop().into(imageAvatar)
            }
        }
    }

    data class Contributor(val user: User, val login: String, val contributions: Int)
    data class User(val name: String?, val location: String?, val blog: String?, val avatar_url: String)
}