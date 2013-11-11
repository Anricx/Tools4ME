package com.gmail.dengtao.joe.tools.page;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.gmail.dengtao.joe.commons.util.CharsetUtils;
import com.gmail.dengtao.joe.commons.util.StringUtils;
import com.gmail.dengtao.joe.qrcode.QRCode;
import com.gmail.dengtao.joe.tools.Window;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodePage implements TabPage {
	
	private ErrorCorrectionLevel level = ErrorCorrectionLevel.L;
	private boolean rebuild = true;
	private boolean generated = false;
	private Charset charset = CharsetUtils.UTF_8;
	
	@Override
	public void create(CTabFolder folder) {
		CTabItem tab = new CTabItem(folder, SWT.NONE);
		tab.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-qrcode-16.png"));
		tab.setText("QRCode");
		
		Composite composite = new Composite(folder, SWT.NONE);
		tab.setControl(composite);
		
		Group plainGroup = new Group(composite, SWT.NONE);
		plainGroup.setText("Charset");
		plainGroup.setBounds(10, 10, 558, 117);
		
		final Text plainText = new Text(plainGroup, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		plainText.setBounds(10, 20, 538, 87);
		
		Group previewGroup = new Group(composite, SWT.NONE);
		previewGroup.setText("Preview");
		previewGroup.setBounds(10, 133, 290, 300);
		
		final Label previewWall = new Label(previewGroup, SWT.NONE);
		previewWall.setLocation(10, 20);
		previewWall.setSize(270, 270);
		previewWall.setText("");
		
		Group charsetGroup = new Group(composite, SWT.NONE);
		charsetGroup.setText("Carset");
		charsetGroup.setBounds(306, 133, 262, 51);
		
		Button charsetUTF8 = new Button(charsetGroup, SWT.RADIO);
		charsetUTF8.setSelection(true);
		charsetUTF8.setBounds(10, 24, 55, 17);
		charsetUTF8.setText("UTF-8");
		
		Button charsetGBK = new Button(charsetGroup, SWT.RADIO);
		charsetGBK.setBounds(82, 24, 46, 17);
		charsetGBK.setText("GBK");
		
		Button charsetISO = new Button(charsetGroup, SWT.RADIO);
		charsetISO.setText("ISO-8859-1");
		charsetISO.setBounds(152, 24, 87, 17);
		
		Group correctGroup = new Group(composite, SWT.NONE);
		correctGroup.setText("Correct Level");
		correctGroup.setBounds(306, 190, 163, 58);
		
		final Combo correctLevel = new Combo(correctGroup, SWT.READ_ONLY);
		correctLevel.setBounds(10, 23, 143, 25);
		correctLevel.add("L = ~7% correction");
		correctLevel.add("M = ~15% correction");
		correctLevel.add("Q = ~25% correction");
		correctLevel.add("H = ~30% correction");
		correctLevel.select(0);
		
		correctLevel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				String levelString = correctLevel.getItem(correctLevel.getSelectionIndex());
				if (levelString.startsWith("L")) QRCodePage.this.level = ErrorCorrectionLevel.L; 
				else if (levelString.startsWith("M")) QRCodePage.this.level = ErrorCorrectionLevel.M; 
				else if (levelString.startsWith("Q")) QRCodePage.this.level = ErrorCorrectionLevel.Q; 
				else if (levelString.startsWith("H")) QRCodePage.this.level = ErrorCorrectionLevel.H; 
				rebuild = true;
			}
		});
		
		Group imageGroup = new Group(composite, SWT.NONE);
		imageGroup.setText("Size");
		imageGroup.setBounds(475, 190, 93, 58);
		
		final Spinner imageSize = new Spinner(imageGroup, SWT.BORDER);
		imageSize.setIncrement(100);
		imageSize.setMaximum(1920);
		imageSize.setMinimum(20);
		imageSize.setSelection(500);
		imageSize.setBounds(10, 25, 73, 23);
		
		Group operateGroup = new Group(composite, SWT.NONE);
		operateGroup.setText("Action");
		operateGroup.setBounds(306, 375, 262, 58);
		
		final Button clean = new Button(operateGroup, SWT.NONE);
		clean.setEnabled(false);
		clean.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-delete-16.png"));
		clean.setBounds(10, 23, 75, 27);
		clean.setText("Clean");
		
		final Button save = new Button(operateGroup, SWT.NONE);
		save.setEnabled(false);
		save.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-save-16.png"));
		save.setBounds(91, 23, 75, 27);
		save.setText("Save");
		
		Button generate = new Button(operateGroup, SWT.NONE);
		generate.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-run-16.png"));
		generate.setBounds(172, 23, 75, 27);
		generate.setText("Gen");
		
		charsetUTF8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				charset = CharsetUtils.UTF_8;
				rebuild = true;
			}
		});
		
		charsetGBK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				charset = CharsetUtils.GBK;
				rebuild = true;
			}
		});

		charsetISO.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				charset = CharsetUtils.ISO_8859_1;
				rebuild = true;
			}
		});
		
		plainText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				rebuild = true;
				generated = false;
				if (StringUtils.isNotBlank(plainText.getText())) {
					clean.setEnabled(true);
				} else {
					clean.setEnabled(false);
				}
			}
		});
		
		clean.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				plainText.setText("");
				previewWall.setImage(null);
				save.setEnabled(false);
			}
		});
		
		save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (generated) {
					String text = plainText.getText();
					int size = imageSize.getSelection();
					if (StringUtils.isNotBlank(text)) {
						try {
							BufferedImage image = QRCode.create(text).withSize(size).withLevel(level).withCharset(charset.name()).toImage();
							FileDialog fileDialog= new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
							fileDialog.setFilterNames(new String[]{"PNG *.png", "JPG *.jpg", "Gif *.gif", "BMP *.bmp"});
							fileDialog.setFilterExtensions(new String[]{ "*.png", "*.jpg", "*.gif", "*.bmp" });
							String savePath = fileDialog.open();
							if (StringUtils.isBlank(savePath)) {
								return;
							}
							ImageIO.write(image, savePath.substring(savePath.lastIndexOf('.') + 1), new File(savePath));
							
							MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_INFORMATION | SWT.OK);
							messageBox.setText("Saved");
					        messageBox.setMessage("QRSaved");
					        messageBox.open();
						} catch (Exception e) {
							MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
							messageBox.setText("Error");
					        messageBox.setMessage("Save error:" + e.getLocalizedMessage());
					        messageBox.open();
						}
					} else {
						MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
						messageBox.setText("Error");
				        messageBox.setMessage("Plz input plain");
				        messageBox.open();
					}
				} else {
					MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
					messageBox.setText("Error");
			        messageBox.setMessage("Push gen pic first");
			        messageBox.open();
				}
				
				
			}
		});
		
		generate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (rebuild) {
					String text = plainText.getText();
					// int size = imageSize.getSelection();
					if (StringUtils.isNotBlank(text)) {
						try {
							File file = QRCode.create(text).withSize(270).withLevel(level).withCharset(charset.name()).toFile();
							previewWall.setImage(SWTResourceManager.getImage(file.getAbsolutePath()));
							save.setEnabled(true);
							rebuild = false;
							generated = true;
						} catch (Exception e) {
							MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
							messageBox.setText("Error");
					        messageBox.setMessage("Save Error:" + e.getLocalizedMessage());
					        messageBox.open();
						}
					} else {
						MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
						messageBox.setText("Error");
				        messageBox.setMessage("Please input content");
				        messageBox.open();
					}
				}
			}
		});
	}
	
}
