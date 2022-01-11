package io.github.aafactory.sample.adapters

import android.app.Activity
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import io.github.aafactory.sample.R
import io.github.aafactory.sample.databinding.ItemBarcodeBinding
import io.github.aafactory.sample.models.Barcode


/**
 * Created by CHO HANJOONG on 2018-04-19.
 */

class BarcodeAdapter(
        private val activity: Activity,
        private val listItem: ArrayList<Barcode>,
        private val onItemClickListener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<BarcodeAdapter.BarcodeViewHolder>() {

    private fun createViewHolder(layoutType: Int, parent: ViewGroup?): BarcodeViewHolder {
        return BarcodeViewHolder(ItemBarcodeBinding.inflate(activity.layoutInflater, parent, false))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarcodeViewHolder = createViewHolder(R.layout.item_barcode, parent)

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: BarcodeViewHolder, position: Int) {
        holder.bindData(listItem[position])
        holder.itemView.setOnClickListener { item ->
            onItemClickListener.onItemClick(null, item, holder.adapterPosition, holder.itemId)
        }
    }

    fun getItem(position: Int): Barcode = listItem[position]

    fun attachTo(view: RecyclerView) {
        view.adapter = this
    }

    class BarcodeViewHolder(var itemBarcodeBinding: ItemBarcodeBinding) : BaseViewHolder<Barcode>(itemBarcodeBinding.root) {
        override fun bindData(barcode: Barcode) {
            itemBarcodeBinding.title.text = barcode.data
        }
    }
}