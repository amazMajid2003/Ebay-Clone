import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebay_clone.Order
import com.example.ebay_clone.R

class OrdersAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderDate: TextView = itemView.findViewById(R.id.orderDate)
        val totalAmount: TextView = itemView.findViewById(R.id.totalAmount)
        val itemsContainer: LinearLayout = itemView.findViewById(R.id.itemsContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.orderDate.text = "Order Date: ${order.orderDate}"
        holder.totalAmount.text = "Total: $${order.totalAmount}"

        // Clear the items container before adding new views
        holder.itemsContainer.removeAllViews()

        // Populate order items
        order.orderItems.forEach { item ->
            val itemView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.item_order_product, holder.itemsContainer, false)
            itemView.findViewById<TextView>(R.id.productName).text = item.productName
            itemView.findViewById<TextView>(R.id.productBrand).text = item.brand
            itemView.findViewById<TextView>(R.id.productPrice).text = "Price: $${item.price}"
            itemView.findViewById<TextView>(R.id.productDiscountedPrice).text =
                "Discounted: $${item.discountedPrice}"
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .into(itemView.findViewById(R.id.productImage))
            holder.itemsContainer.addView(itemView)
        }
    }

    override fun getItemCount() = orders.size
}
