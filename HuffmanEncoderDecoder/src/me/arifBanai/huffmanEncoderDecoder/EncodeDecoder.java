package me.arifBanai.huffmanEncoderDecoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;

//Contains methods for encoding text from and to a file, and decoding text from and to a file
public class EncodeDecoder {

	public static void encodeRawText(String srcFileName, String targetFileName) throws IOException {

		File inputRaw = new File(srcFileName);
		BufferedReader br = new BufferedReader(new FileReader(inputRaw));

		HashMap<Character, Integer> frequency = new HashMap<>();

		//Create character-frequency mapping
		String line = "";
		while ((line = br.readLine()) != null) {
			for (char x : line.toCharArray()) {
				int count = frequency.containsKey(x) ? frequency.get(x) : 0;

				frequency.put(x, count + 1);
			}
		}

		br.close();

		//Create the Priority Queue
		PriorityQueue pq = buildPriorityQueue(frequency);

		//Get the root of the huffman tree using the Priority Queue
		HuffmanNode root = buildHuffmanTree(pq);

		//Build encoding table using frequency hash map and root of huffman tree
		HashMap<Character, String> encodingTable = buildEncodingTable(frequency, root);

		//Print encodingTable to first line of output
		PrintWriter encodedOut = new PrintWriter(targetFileName);

		//Transfer the encoding table's entries into the first line of the file
		String firstLine = "";
		for (char x : encodingTable.keySet()) {
			if (firstLine.equals("")) {
				firstLine = "" + x;
			} else {
				firstLine += "," + x;
			}

			String code = encodingTable.get(x);
			firstLine += code;
		}

		encodedOut.write(firstLine + "\n");

		//Convert the text in the input file to a bitstring using the encoding table
		//and write it to the output file, line by line
		BufferedReader newBr = new BufferedReader(new FileReader(inputRaw));

		String encoded = "";
		while ((line = newBr.readLine()) != null) {
			for (char x : line.toCharArray()) {
				encoded += encodingTable.get(x);
			}

			String encodedString = Base64.getEncoder().encodeToString(encoded.getBytes());
			encodedOut.write(encodedString + "\n");

			encoded = "";
		}

		encodedOut.close();
		newBr.close();
	}

	
	//TODO Reconstruct HuffmanTree for decoding
	// Instead of reconstructing the HuffmanTree, every time a character is read,
	//    the character is appended to a String, and then a check is done to see
	//    if the String matches a encoding from the encodingTable
	// This should be replaced with an actual reconstruction of the HuffmanTree from
	//    the encodingTable
	public static void decodeText(String srcFileName, String targetFileName) throws IOException {
		File encodedText = new File(srcFileName);
		BufferedReader br = new BufferedReader(new FileReader(encodedText));

		PrintWriter outputDecodedText = new PrintWriter(targetFileName);

		HashMap<String, Character> reverseEncodingTable = new HashMap<>();

		String line = "";
		boolean firstLine = true;

		String decoded = "";

		//Read the input file line by line
		while ((line = br.readLine()) != null) {
			if (firstLine) {
				//When reading first line of the file, fill the reverse encoding table
				String[] codeTableEntries = line.split(",");

				for (String x : codeTableEntries) {
					char[] array = x.toCharArray();

					char key = array[0];
					String code = "";

					for (int i = 1; i < array.length; i++) {
						code += array[i];
					}

					reverseEncodingTable.put(code, key);
					code = "";
				}

				firstLine = false;
			} else {
				//The rest of the file contains encoded Strings.
				//Decode them and write to the output file
				byte[] valueDecoded = Base64.getDecoder().decode(line);
				line = new String(valueDecoded);

				String currentBitString = "";

				for (char x : line.toCharArray()) {
					currentBitString += x;

					if (reverseEncodingTable.containsKey(currentBitString)) {
						char value = reverseEncodingTable.get(currentBitString);

						decoded += value;
						currentBitString = "";
					}
				}

				outputDecodedText.write(decoded + "\n");
				decoded = "";
			}
		}

		outputDecodedText.close();
		br.close();
	}

	//Build the Priority Queue using the frequency table
	public static PriorityQueue buildPriorityQueue(HashMap<Character, Integer> frequency) {

		PriorityQueue pq = new PriorityQueue();

		//Put all HuffmanNodes into Min-Heap PriorityQueue
		for (char x : frequency.keySet()) {
			String text = "" + x;
			int count = frequency.get(x);

			HuffmanNode node = new HuffmanNode(text, count);

			PriorityQueueNode capsule = new PriorityQueueNode(node);

			pq.insert(capsule);
		}

		return pq;
	}

	//Build the huffman tree using the Priority Queue
	public static HuffmanNode buildHuffmanTree(PriorityQueue pq) {

		while (pq.size() > 1) {

			HuffmanNode one = pq.removeRoot().data;
			HuffmanNode two = pq.removeRoot().data;

			HuffmanNode parent = new HuffmanNode(one, two);
			PriorityQueueNode capsule = new PriorityQueueNode(parent);

			pq.insert(capsule);
		}

		return pq.getRoot().data;
	}

	//Build the encoding table using the frequency table and root of the huffman tree
	public static HashMap<Character, String> buildEncodingTable(HashMap<Character, Integer> frequency,
			HuffmanNode node) {

		HashMap<Character, String> encodingTable = new HashMap<>();

		String text = "";
		for (char x : frequency.keySet()) {
			text = "" + x;
			String encoding = HuffmanNode.getEncoding(node, text);

			encodingTable.put(x, encoding);

			text = "";
		}

		return encodingTable;
	}

}
