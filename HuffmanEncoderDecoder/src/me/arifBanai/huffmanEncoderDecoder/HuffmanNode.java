package me.arifBanai.huffmanEncoderDecoder;

public class HuffmanNode {
	String text;
	int count;

	HuffmanNode huffLeft;
	HuffmanNode huffRight;
	HuffmanNode huffParent;

	public HuffmanNode(String t, int c) {
		text = t;
		count = c;
	}
	
	public HuffmanNode(HuffmanNode n) {
		text = n.text;
		count = n.count;
		
		huffLeft = n.huffLeft;
		huffRight = n.huffRight;
		huffParent = n.huffParent;
	}

	public HuffmanNode(HuffmanNode left, HuffmanNode right) {
		text = left.text + right.text;
		count = left.count + right.count;
		huffLeft = left;
		left.huffParent = this;
		huffRight = right;
		right.huffParent = this;
	}
	
	//Used for ordering the pq 
	public int compareTo(HuffmanNode x) { 
        return this.count - x.count;
    } 

	public String toString() {
		return text + ":" + count;
	}

	//Get bitstring corresponding to its path for a character in the huffman tree 
	public static String getEncoding(HuffmanNode node, String targetCharacter) {

		String result = "";
		
		while(node.huffLeft != null) {

			if (node.huffLeft.text.contains(targetCharacter)) {
				node = node.huffLeft;
				result += "0";
			} else {
				node = node.huffRight;
				result += "1";
			}

		}

		return result;
	}
}
