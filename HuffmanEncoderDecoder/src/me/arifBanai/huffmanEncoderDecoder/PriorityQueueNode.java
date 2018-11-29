package me.arifBanai.huffmanEncoderDecoder;

//Priority Queue capsule node for HuffmanNode to implement doubly linked list
public class PriorityQueueNode {

	public HuffmanNode data;
	public PriorityQueueNode next;
	public PriorityQueueNode prev;

	public PriorityQueueNode() {
		this.data = null;
		this.next = null;
		this.prev = null;
	}
	
	public PriorityQueueNode(HuffmanNode data) {
		this.data = data;
		this.next = null;
		this.prev = null;
	}

	public PriorityQueueNode(HuffmanNode data, PriorityQueueNode left) {
		this.data = data;
		this.next = left;
		this.prev = null;
	}
	
	public PriorityQueueNode(HuffmanNode data, PriorityQueueNode left, PriorityQueueNode right) {
		this.data = data;
		this.next = left;
		this.prev = right;
	}

}
