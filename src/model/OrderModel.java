package model;

import java.util.List;

public class OrderModel {
	
	private List<Order> orderList;
	
	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public class Order
	{
		private String _id;
		private String name;
		
		public String getId() {
			return _id;
		}
		public String getName() {
			return name;
		}
		public void setId(String _id) {
			this._id = _id;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

}
