package com.gmail.dengtao.joe.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;

public class QRCode {

	private QRCode() { }

	/**
     * Create a QR code from the given text.    <br/><br/>
     * 
     * There is a size limitation to how much you can put into a QR code. This has been tested to work with up to a length of 2950 characters.<br/><br/>
     * 
     * The QRCode will have the following defaults:     <br/>
     * {size: 125 X 125}<br/>{imageType:PNG}  <br/><br/>
     * 
     * Both size and imageType can be overridden:   <br/>
     * Image type override is done by calling {@link QRCodeBuilder#to(com.llt.qrcode.image.ImageType)} e.g. QRCode.from("hello world").to(JPG) <br/>
     * Size override is done by calling {@link QRCodeBuilder#withSize} e.g. QRCode.from("hello world").to(JPG).withSize(125, 125)  <br/>
     * 
     * @param text the text to encode to a new QRCode, this may fail if the text is too large. <br/>
     * @return the QRCode object    <br/>
     */
	public static QRCodeBuilder create(String text) {
		return QRCodeBuilder.from(text);
	}
	
	public static QRCodeResolver resolve(File file) {
		return QRCodeResolver.newInstance(file);
	}
	
	public static QRCodeResolver resolve(BufferedImage image) {
		return QRCodeResolver.newInstance(image);
	}

}