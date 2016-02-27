import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import model.OrderModel;
import model.OrderModel.Order;
import model.OrderTrie;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * This class will help in filtering orders by Order Name.
 * Functionality - All orders are first converted into a Trie, and then the search string is searched.
 * Time Complexity = O(n) where n = length of the search string.
 * 
 * @author global
 *
 */
public class OrderHistory {

	public static void main(String[] args) {
		
		// The below 2 operations will be a one time operation.
		OrderModel listOrders = readOrders();
		OrderTrie orderSorted = setOrdersIntoTrie(listOrders);
		
		// Searching for a string in the trie.
		searchString(orderSorted,"is");

	}

	/**
	 * @param orderSorted - Root node of the trie.
	 * @param searchString - String to be searched.
	 */
	private static void searchString(OrderTrie orderSorted, String searchString) 
	{
		for(int count = 0 ; count < searchString.length() ; count++)
		{
			OrderTrie node = orderSorted.getChildNode(Character.toLowerCase(searchString.charAt(count)));
			if(node == null)
			{
				System.out.println("Sorry! String not found");
			}
			orderSorted = node;
		}
		if(!orderSorted.getAssociateOrder().isEmpty())
		{
			System.out.println("There are "+orderSorted.getAssociateOrder().size()+" orders with the name "+searchString);
		}
	}

	/**
	 * This is a one time conversion from the data received from server into trie data
	 * structure,which will help in search.
	 * Each node will hold array of Orders.
	 * @param listOrders
	 */
	private static OrderTrie setOrdersIntoTrie(OrderModel listOrders) {
		
		if(null != listOrders && null != listOrders.getOrderList() && listOrders.getOrderList().size() > 0)
		{
			OrderTrie parentNode = new OrderTrie();
			for(int count = 0 ; count < listOrders.getOrderList().size(); count++)
			{
				Order order = listOrders.getOrderList().get(count);
				
				if(null != order && null != order.getName() && order.getName().length() > 0)
				{
					String orderName = order.getName();

					String[] actualWords = orderName.split(" ");
					for(int charCount = 0 ; charCount < actualWords.length; charCount++)
					{
						OrderTrie tempNode = parentNode;
						for(int j = 0;j< actualWords[charCount].length();j++)
						{
							OrderTrie childNode = tempNode.getChildNode(actualWords[charCount].charAt(j));
							childNode.setAssociateOrder(order);
							if(childNode.getCharacter() == null)
							{
								childNode.setCharacter(Character.toLowerCase(actualWords[charCount].charAt(j))+"");
								tempNode.setChildNode(childNode);
							}
							tempNode = childNode;
						}
					}
				}
			}
			return parentNode;
		}
		
		return null;
		
	}

	/**
	 * One time read orders from text file. This data will be ideally received from server.
	 * @return
	 */
	private static OrderModel readOrders() {
		JSONParser parser = new JSONParser();
		OrderModel orderList = new OrderModel();
		List<Order> orders = new ArrayList<>();

		try {

			Object obj = parser.parse(new FileReader(
					"./properties/orders.txt"));

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray storeList = (JSONArray) jsonObject.get("orders");
			

			for(Object data:storeList)
			{
				JSONObject orderData = (JSONObject)data;
				Order order = orderList.new Order();
				String orderName = (String)orderData.get("name");
				String _id = (String)orderData.get("_id");
				order.setId(_id);
				order.setName(orderName);
				orders.add(order);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		orderList.setOrderList(orders);

		return orderList;
	}

}
