package me.arifBanai.huffmanEncoderDecoder;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		EncodeDecoder.encodeRawText("oneRawToCode.txt", "oneEncodedToRaw.txt");
		
		EncodeDecoder.decodeText("oneEncodedToRaw.txt", "oneDecodedText.txt");
	}
}
