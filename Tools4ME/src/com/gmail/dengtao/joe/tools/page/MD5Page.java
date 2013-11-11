package com.gmail.dengtao.joe.tools.page;

import java.nio.charset.Charset;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.gmail.dengtao.joe.commons.util.CharsetUtils;
import com.gmail.dengtao.joe.commons.util.MD5;
import com.gmail.dengtao.joe.commons.util.StringUtils;
import com.gmail.dengtao.joe.tools.Window;

public class MD5Page implements TabPage {

	private Charset MD5_CHARSET = CharsetUtils.UTF_8; 
	private Clipboard clipboard;	// 
	private TextTransfer textTransfer = TextTransfer.getInstance();
	
	@Override
	public void create(CTabFolder folder) {
		clipboard = new Clipboard(folder.getDisplay());

		CTabItem tab = new CTabItem(folder, SWT.NONE);
		tab.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-md5-16.png"));
		tab.setText("MD5");
		
		Composite composite = new Composite(folder, SWT.NONE);
		tab.setControl(composite);
		
		Group plainGroup = new Group(composite, SWT.NONE);
		plainGroup.setBounds(10, 10, 558, 200);
		plainGroup.setText("Plain");
		
		Composite plainComposite = new Composite(plainGroup, SWT.NONE);
		plainComposite.setBounds(10, 20, 538, 170);
		
		final Text plainText = new Text(plainComposite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		plainText.setBounds(0, 0, 538, 170);
		
		Group optionsGroup = new Group(composite, SWT.NONE);
		optionsGroup.setBounds(10, 215, 558, 46);
		optionsGroup.setText("Charset");
		
		Button charsetUTF8 = new Button(optionsGroup, SWT.RADIO);
		charsetUTF8.setText("UTF-8");
		charsetUTF8.setSelection(true);
		charsetUTF8.setBounds(10, 22, 55, 21);
		
		Button charsetGBK = new Button(optionsGroup, SWT.RADIO);
		charsetGBK.setText("GBK");
		charsetGBK.setBounds(71, 22, 46, 21);
		
		Button charsetGB2312 = new Button(optionsGroup, SWT.RADIO);
		charsetGB2312.setText("GB2312");
		charsetGB2312.setBounds(123, 22, 66, 21);
		
		Group resultGroup = new Group(composite, SWT.NONE);
		resultGroup.setBounds(10, 267, 558, 166);
		resultGroup.setLayout(null);
		resultGroup.setText("Result");
		
		Label label32HEX = new Label(resultGroup, SWT.NONE);
		label32HEX.setBounds(13, 30, 58, 17);
		label32HEX.setText("32BIT");
		
		final Text result32HEXText = new Text(resultGroup, SWT.BORDER);
		result32HEXText.setBounds(77, 27, 437, 24);
		
		Button copy32HEX = new Button(resultGroup, SWT.NONE);
		copy32HEX.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-copy-16.png"));
		copy32HEX.setBounds(520, 25, 28, 27);
		
		Label label32Hex = new Label(resultGroup, SWT.NONE);
		label32Hex.setBounds(13, 64, 58, 17);
		label32Hex.setText("32bit");
		
		final Text result32HexText = new Text(resultGroup, SWT.BORDER);
		result32HexText.setBounds(77, 61, 437, 24);
		
		Button copy32Hex = new Button(resultGroup, SWT.NONE);
		copy32Hex.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-copy-16.png"));
		copy32Hex.setBounds(520, 59, 28, 27);
		
		Label label16HEX = new Label(resultGroup, SWT.NONE);
		label16HEX.setBounds(13, 101, 58, 17);
		label16HEX.setText("16BIT");
		
		final Text result16HEXText = new Text(resultGroup, SWT.BORDER);
		result16HEXText.setBounds(77, 98, 437, 24);
		
		Button copy16HEX = new Button(resultGroup, SWT.NONE);
		copy16HEX.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-copy-16.png"));
		copy16HEX.setBounds(520, 96, 28, 27);
		
		Label label16Hex = new Label(resultGroup, SWT.SHADOW_IN);
		label16Hex.setBounds(13, 134, 58, 17);
		label16Hex.setText("16bit");
		
		final Text result16HexText = new Text(resultGroup, SWT.BORDER);
		result16HexText.setBounds(77, 131, 437, 24);
		
		Button copy16Hex = new Button(resultGroup, SWT.NONE);
		copy16Hex.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-copy-16.png"));
		copy16Hex.setBounds(520, 129, 28, 27);
		
		plainText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String plain = plainText.getText();
				if (StringUtils.isNotBlank(plain)) {
					String md5 = MD5.encryptHex(plain.getBytes(MD5_CHARSET));
					result32HEXText.setText(md5.toUpperCase());
					result32HexText.setText(md5);
					result16HEXText.setText(md5.substring(8, 24).toUpperCase());
					result16HexText.setText(md5.substring(8, 24));
				} else {
					result32HEXText.setText("");
					result32HexText.setText("");
					result16HEXText.setText("");
					result16HexText.setText("");
				}
			}
		});
		
		charsetUTF8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MD5_CHARSET = CharsetUtils.UTF_8;
				String plain = plainText.getText();
				if (StringUtils.isNotBlank(plain)) {
					String md5 = MD5.encryptHex(plain.getBytes(MD5_CHARSET));
					result32HEXText.setText(md5.toUpperCase());
					result32HexText.setText(md5);
					result16HEXText.setText(md5.substring(8, 24).toUpperCase());
					result16HexText.setText(md5.substring(8, 24));
				} else {
					result32HEXText.setText("");
					result32HexText.setText("");
					result16HEXText.setText("");
					result16HexText.setText("");
				}
			}
		});
		
		charsetGBK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MD5_CHARSET = CharsetUtils.GBK;
				String plain = plainText.getText();
				if (StringUtils.isNotBlank(plain)) {
					String md5 = MD5.encryptHex(plain.getBytes(MD5_CHARSET));
					result32HEXText.setText(md5.toUpperCase());
					result32HexText.setText(md5);
					result16HEXText.setText(md5.substring(8, 24).toUpperCase());
					result16HexText.setText(md5.substring(8, 24));
				} else {
					result32HEXText.setText("");
					result32HexText.setText("");
					result16HEXText.setText("");
					result16HexText.setText("");
				}
			}
		});
		
		charsetGB2312.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MD5_CHARSET = CharsetUtils.GB2312;
				String plain = plainText.getText();
				if (StringUtils.isNotBlank(plain)) {
					String md5 = MD5.encryptHex(plain.getBytes(MD5_CHARSET));
					result32HEXText.setText(md5.toUpperCase());
					result32HexText.setText(md5);
					result16HEXText.setText(md5.substring(8, 24).toUpperCase());
					result16HexText.setText(md5.substring(8, 24));
				} else {
					result32HEXText.setText("");
					result32HexText.setText("");
					result16HEXText.setText("");
					result16HexText.setText("");
				}
			}
		});
		
		copy32HEX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNotBlank(result32HEXText.getText())) {
					clipboard.setContents(new Object[] { result32HEXText.getText() }, new TextTransfer[] { textTransfer });
				} else {
					clipboard.clearContents();
				}
			}
		});
		
		copy32Hex.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNotBlank(result32HexText.getText())) {
					clipboard.setContents(new Object[] { result32HexText.getText() }, new TextTransfer[] { textTransfer });
				} else {
					clipboard.clearContents();
				}
			}
		});

		copy16HEX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNotBlank(result16HEXText.getText())) {
					clipboard.setContents(new Object[] { result16HEXText.getText() }, new TextTransfer[] { textTransfer });
				} else {
					clipboard.clearContents();
				}
			}
		});
		
		copy16Hex.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNotBlank(result16HexText.getText())) {
					clipboard.setContents(new Object[] { result16HexText.getText() }, new TextTransfer[] { textTransfer });
				} else {
					clipboard.clearContents();
				}
			}
		});
	}

}
