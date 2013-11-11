package com.gmail.dengtao.joe.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeResolver {

	private BufferedImage image;
	
	QRCodeResolver(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	QRCodeResolver(BufferedImage image) {
		this.image = image;
	}

	static QRCodeResolver newInstance(BufferedImage image) {
		return new QRCodeResolver(image);
	}
	
	static QRCodeResolver newInstance(File file) {
		return new QRCodeResolver(file);
	}

	private Result decode() throws NotFoundException {
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();  
    	hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
    	//hints.put(DecodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		Result result = new MultiFormatReader().decode(bitmap, hints);
		return result;
	}
	
	@Override
	public String toString() {
		try {
			Result result = decode();
			return result.getText();
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}