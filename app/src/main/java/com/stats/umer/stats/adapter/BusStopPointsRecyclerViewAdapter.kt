package com.stats.umer.stats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.stats.umer.stats.R
import com.stats.umer.stats.model.uiDTOs.SeatReservationScreenDTO

class BusStopPointsRecyclerViewAdapter(itemClickListener: StopItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mItemClickListener: StopItemClickListener = itemClickListener
    var mBusStopDataSource: ArrayList<SeatReservationScreenDTO> = ArrayList();

    fun setDataSource(dataSource: ArrayList<SeatReservationScreenDTO>)
    {
        mBusStopDataSource = dataSource
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view  = inflater.inflate(R.layout.stop_list_recycler_view_item, parent, false)
        return BusStopPointsItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mBusStopDataSource.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as BusStopPointsItemViewHolder
        viewHolder.bindStopInfo(mBusStopDataSource[position])
    }

    inner class BusStopPointsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        @BindView(R.id.tvBusStopName)
        lateinit var busStopName: TextView
        init {
            ButterKnife.bind(this, itemView)
        }
        fun bindStopInfo(busStopInfo: SeatReservationScreenDTO)
        {
            busStopName.text = busStopInfo.stopName
            itemView.setOnClickListener {
                mItemClickListener.onStopItemClicked(busStopInfo)
            }
        }
    }

    interface StopItemClickListener
    {
        fun onStopItemClicked(stopInfo: SeatReservationScreenDTO)
    }

}