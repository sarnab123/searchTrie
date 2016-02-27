package model;

import java.util.ArrayList;
import java.util.List;

import model.OrderModel.Order;

public class OrderTrie {

	List<OrderTrie> nodeList = new ArrayList<>(26);
	
	public OrderTrie()
	{
		for(int i = 0 ; i < 26; i++)
		{
			nodeList.add(null);
		}
	}

	public OrderTrie getChildNode(char nodeIndex) {
		if(' ' != nodeIndex && Character.isLetter(nodeIndex))
		{
			int asciiIndex = Character.toLowerCase(nodeIndex) - 97;
			if(nodeList.get(asciiIndex) != null && nodeList.get(asciiIndex).getCharacter() != null)
			{
				return nodeList.get(asciiIndex);
			}
		}
		return new OrderTrie();

	}
	
	public void setChildNode(OrderTrie child)
	{
		int asciiIndex = Character.toLowerCase(child.getCharacter().charAt(0)) - 97;
		nodeList.set(asciiIndex, child);
	}

	private String character;
	private List<Order> associateOrder = new ArrayList<Order>();

	public String getCharacter() {
		return character;
	}

	public List<Order> getAssociateOrder() {
		return associateOrder;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public void setAssociateOrder(Order indOrder) {
		this.associateOrder.add(indOrder);
	}

}
