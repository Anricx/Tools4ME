package com.gmail.dengtao.joe.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.gmail.dengtao.joe.commons.util.CharsetUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * QRCode generator. This is a simple class that is built on top of <a href="http://code.google.com/p/zxing/">ZXING</a><br/><br/>
 *
 * Please take a look at their framework, as it has a lot of features. <br/>
 * This small project is just a wrapper that gives a convenient interface to work with. <br/><br/>
 *
 * Start here: {@link QRCodeBuilder#from(String)} (e.g QRCode.from("hello"))
 */
public class QRCodeBuilder {

    private final String text;
    private int size = 125;
    private int margin = 0;
    private ImageType imageType = ImageType.PNG;
    private String charset = "UTF-8";
    private ErrorCorrectionLevel level = ErrorCorrectionLevel.H;
    private BufferedImage logo = null;
    private int logoWidth = -1;

    private QRCodeBuilder(String text) {
        this.text = text;
    }

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
    static QRCodeBuilder from(String text) {
        return new QRCodeBuilder(text);
    }

    /**
     * Overrides the imageType from its default {@link ImageType#PNG}
     * @param imageType the {@link ImageType} you would like the resulting QR to be
     * @return the current QRCode object
     */
    public QRCodeBuilder to(ImageType imageType) {
        this.imageType = imageType;
        return this;
    }

    /**
     * Overrides the margin of the qr from its default 0
     * @param margin the margin in pixels
     * @return the current QRCode object
     */
    public QRCodeBuilder withMargin(int margin) {
    	if (margin < 1) throw new IllegalArgumentException();
    	this.margin = margin;
    	return this;
    }
    
    /**
     * Overrides the size of the qr from its default 125x125
     * @param size the size in pixels
     * @return the current QRCode object
     */
    public QRCodeBuilder withSize(int size) {
    	if (size < 1) throw new IllegalArgumentException();
        this.size = size;
        return this;
    }
    
    /**
     * Overrides the error correction level of the qr from its default ErrorCorrectionLevel.H
     * @param level the error correction level
     * @return the current QRCode object
     */
    public QRCodeBuilder withLevel(ErrorCorrectionLevel level) {
    	if (level == null) return this;
    	this.level = level;
        return this;
    }

    /**
     * Add logo image to current qr
     * @param logo logo you need
     * @param logoWidth the logo's width
     * @return the current QRCode object
     */
	public QRCodeBuilder withLogo(BufferedImage logo, int logoWidth) {
		if (logo != null && logoWidth < 1) throw new IllegalArgumentException("logo width invalid!");
		this.logo = logo;
		this.logoWidth = logoWidth;
		return this;
	}
	
	/**
     * Add encode charset, to replace default charset UTF-8
     * @param charset new charset
     * @return the current QRCode object
     */
	public QRCodeBuilder withCharset(String charset) {
		this.charset = CharsetUtils.toCharset(charset).name();
		return this;
	}
	
    /**
	 * returns a {@link ByteArrayOutputStream} representation of the QR code
	 * @return qrcode as stream
	 */
	public ByteArrayOutputStream toStream() {
	    try {
	    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    	BufferedImage image = toImage();
        	ImageIO.write(image, imageType.toString(), stream);
	    	return stream;
	    } catch (Exception e) {
	        throw new QRGenerationException("Failed to create QR image from text due to underlying exception", e);
	    }
	
	}

	/**
	 * returns a {@link BufferedImage} representation of the QR code
	 * @return qrcode as BufferedImage
	 */
	public BufferedImage toImage() {
		try {
			BitMatrix matrix = createMatrix();
		    BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
		    image.createGraphics();
	        Graphics2D graphics = (Graphics2D) image.getGraphics();  
		    graphics.setColor(Color.WHITE);  
	        graphics.fillRect(0, 0, matrix.getWidth(), matrix.getHeight());  
		    
	        // Paint and save the image using the ByteMatrix  
	        graphics.setColor(Color.BLACK);  
	        for (int i = 0; i < matrix.getWidth(); i++){  
	            for (int j = 0; j < matrix.getHeight(); j++){  
	                if (matrix.get(i, j)){  
	                    graphics.fillRect(i, j, 1, 1);  
	                }  
	            }  
	        }
	        if (logo != null) {
	        	graphics.drawImage(logo, (image.getWidth() - logoWidth) / 2, (image.getHeight() - logoWidth) / 2, logoWidth, logoWidth, null);
			}
	        graphics.dispose();
		    return image;
		} catch (Exception e) {
	        throw new QRGenerationException("Failed to create QR image from text due to underlying exception", e);
		}
	}

	/**
	 * returns a {@link File} representation of the QR code. The file is set to be deleted on exit (i.e. {@link java.io.File#deleteOnExit()}).
	 * If you want the file to live beyond the life of the jvm process, you should make a copy.
	 * @return qrcode as file
	 */
	public File toFile() {
	    try {
	    	File file = createTempFile();
	    	BufferedImage image = toImage();
	    	ImageIO.write(image, imageType.toString(), file);
	        return file;
	    } catch (Exception e) {
	        throw new QRGenerationException("Failed to create QR image from text due to underlying exception", e);
	    }
	}

    /**
     * writes a representation of the QR code to the supplied  {@link OutputStream}
     * @param stream the {@link OutputStream} to write QR Code to
     */
    public void writeTo(OutputStream stream) {
        try {
        	BufferedImage image = toImage();
        	ImageIO.write(image, imageType.toString(), stream);
        } catch (Exception e) {
            throw new QRGenerationException("Failed to create QR image from text due to underlying exception", e);
        }
    }

    private BitMatrix createMatrix() throws WriterException {
    	Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
    	hints.put(EncodeHintType.MARGIN, margin);
    	hints.put(EncodeHintType.CHARACTER_SET, charset);
    	hints.put(EncodeHintType.ERROR_CORRECTION, level);  
    	return new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints);  
    }

    private File createTempFile() throws IOException {
        File file = File.createTempFile("QRCode", "."+imageType.toString().toLowerCase());
        file.deleteOnExit();
        return file;
    }
    
    public static enum ImageType {
        JPG, GIF, PNG
    }

    public static class QRGenerationException extends RuntimeException {

    	private static final long serialVersionUID = -6050587520993670908L;

    	public QRGenerationException(String message, Throwable underlyingException) {
            super(message, underlyingException);
        }
    	
    }
}