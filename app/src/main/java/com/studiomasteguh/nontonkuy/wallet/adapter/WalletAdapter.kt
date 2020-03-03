package com.studiomasteguh.nontonkuy.wallet.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.wallet.model.Wallet
import java.text.NumberFormat
import java.util.*

class WalletAdapter(private var data: List<Wallet>,
                    private val listener: (Wallet) -> Unit)
    : RecyclerView.Adapter<WalletAdapter.LeagueViewHolder>() {

    private lateinit var ContextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_transaksi, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }


    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tv_title_transaction)

        private val tvDate: TextView = view.findViewById(R.id.tv_date_wallet)

        private val tvSaldo: TextView = view.findViewById(R.id.tv_saldo_wallet)


        fun bindItem(data: Wallet, listener: (Wallet) -> Unit, context: Context, position: Int) {

            tvTitle.text = data.title
            tvDate.text = data.date

            val localID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)

            if (data.status.equals("0")){
                tvSaldo.text = "-" +formatRupiah.format(data.money.toDouble())
            } else{
                tvSaldo.text = "+" +formatRupiah.format(data.money.toDouble())
                tvSaldo.setTextColor(Color.GREEN)
            }

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}
