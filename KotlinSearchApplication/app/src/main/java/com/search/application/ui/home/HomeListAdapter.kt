package com.v1.application.view.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.search.application.databinding.AdapterHomeListItemBinding
import com.search.application.model.HomeListModel.HomeItemModel
import com.v1.application.utils.LogUtils


import java.lang.ref.WeakReference


class HomeListAdapter(callbak: HomeItemCallback) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val itemList : MutableList<HomeItemModel> = ArrayList()
    private val callback : WeakReference<HomeItemCallback> = WeakReference(callbak)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            AdapterHomeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder ->{
                callback.get()?.let {
                    if(itemList.size > 0 && position < itemList.size){
                        holder.bind(itemList[position], it)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }

    fun setItemList(newItems: List<HomeItemModel>?){
        updateList(newItems)
    }

    fun getItemPosition(item: HomeItemModel):Int{
        return itemList.indexOf(item)
    }

    fun clear(){
        itemList.clear()
    }

    fun updateList(newItems: List<HomeItemModel>?) {
        newItems?.let { items ->
            this.itemList.run {
                clear()
                addAll(items)
            }
        }
    }

    inner class ViewHolder(private val binding: AdapterHomeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeItemModel?, callback: HomeItemCallback) {
            with(binding) {
                item?.let { item ->
                    this.data = item
                    this.callback = callback
                }
            }
        }
    }


}