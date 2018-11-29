package me.arifBanai.huffmanEncoderDecoder;

//Priority Queue using a doubly linked list with a dummy head node
public class PriorityQueue {

	private PriorityQueueNode root;
	private int size;

	//Initialize root and size
	public PriorityQueue() {
		root = new PriorityQueueNode();
		size = 0;
	}

	//Return the size of the pq
	public int size() {
		return size;
	}

	//Check if the pq is empty
	public boolean isEmpty() {
		return size == 0;
	}

	//Get the root node
	public PriorityQueueNode getRoot() {
		return root.next;
	}

	//Get and remove the root node and update the root node
	public PriorityQueueNode removeRoot() {
		if (size == 0) {
			return null;
		}

		PriorityQueueNode targetNode = root.next;

		if (size == 1) {
			root.next = null;
		} else {
			root.next = root.next.next;
			root.next.prev = null;
		}

		size--;
		
		return targetNode;
	}

	//Insert a node into the LinkedList
	public void insert(PriorityQueueNode node) {
		if (size == 0) {
			root.next = node;
			size++;
			return;
		}

		//Loop through LinkedList until the node is inserted
		for (PriorityQueueNode currentNode = root.next; currentNode != null; currentNode = currentNode.next) {
			if (currentNode.data.compareTo(node.data) >= 1) {
				if (currentNode.prev != null) {
					//Insert node between two nodes
					node.next = currentNode;
					node.prev = currentNode.prev;
					currentNode.prev.next = node;
					currentNode.prev = node;

					size++;
					
					return;
				} else {
					//Prepend the node
					node.next = root.next;
					root.next.prev = node;
					root.next = node;

					size++;
					
					return;
				}
			} else {
				//currentNode is not target for insertion, check if end of list was reached
				if (currentNode.next != null) {
					//End of the list not reached
					continue;
				} else {
					//End of list reached, append node to the end
					currentNode.next = node;
					node.prev = currentNode;

					size++;
					
					return;
				}
			}
		}
	}

	//Print every node in order
	public void printPQ() {
		for (PriorityQueueNode currentNode = root.next; currentNode != null; currentNode = currentNode.next) {
			System.out.print(currentNode.data + ", ");
		}
	}
}
