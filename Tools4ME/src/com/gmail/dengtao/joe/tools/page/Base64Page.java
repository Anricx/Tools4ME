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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.gmail.dengtao.joe.commons.util.Base64Utils;
import com.gmail.dengtao.joe.commons.util.CharsetUtils;
import com.gmail.dengtao.joe.commons.util.StringUtils;
import com.gmail.dengtao.joe.tools.Window;

public class Base64Page implements TabPage {

	private Clipboard clipboard;	// 
	private TextTransfer textTransfer = TextTransfer.getInstance();
	
	private boolean BASE64_PLAIN_MODIFY_FLAG = true;
	private boolean BASE64_ENCODED_MODIFY_FLAG = true;
	private boolean BASE64_CURRENT_MODIFY_PLAIN = true;
	private Charset BASE64_CHARSET = CharsetUtils.UTF_8; 

	@Override
	public void create(CTabFolder folder) {
		clipboard = new Clipboard(folder.getDisplay());
		
		CTabItem tab = new CTabItem(folder, SWT.NONE);
		tab.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-base64-16.png"));
		tab.setText("Base64");
		
		Composite composite = new Composite(folder, SWT.NONE);
		tab.setControl(composite);
		
		Group plainGroup = new Group(composite, SWT.NONE);
		plainGroup.setBounds(10, 10, 558, 175);
		plainGroup.setText("Plain");
		
		Composite plainComposite = new Composite(plainGroup, SWT.NONE);
		plainComposite.setBounds(10, 20, 538, 145);
		
		final Text plainText = new Text(plainComposite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		plainText.setBounds(0, 0, 538, 145);
		
		Group charsetGroup = new Group(composite, SWT.NONE);
		charsetGroup.setBounds(10, 189, 301, 60);
		charsetGroup.setText("Charset");
		
		Button charsetRadioUTF8 = new Button(charsetGroup, SWT.RADIO);
		charsetRadioUTF8.setBounds(10, 22, 55, 28);
		charsetRadioUTF8.setText("UTF-8");
		charsetRadioUTF8.setSelection(true);
		
		Button charsetRadioGBK = new Button(charsetGroup, SWT.RADIO);
		charsetRadioGBK.setBounds(71, 22, 46, 28);
		charsetRadioGBK.setText("GBK");

		Button charsetRadioGB2312 = new Button(charsetGroup, SWT.RADIO);
		charsetRadioGB2312.setBounds(123, 22, 66, 28);
		charsetRadioGB2312.setText("GB2312");
		
		Group operateGroup = new Group(composite, SWT.NONE);
		operateGroup.setBounds(317, 189, 251, 60);
		operateGroup.setText("Action");
		
		Button operateClean = new Button(operateGroup, SWT.NONE);
		operateClean.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-delete-16.png"));
		
		operateClean.setBounds(10, 23, 74, 27);
		operateClean.setText("Clean");
		
		Button operateCopyPlain = new Button(operateGroup, SWT.NONE);
		operateCopyPlain.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-copy-white-16.png"));
		operateCopyPlain.setText("Plain");
		operateCopyPlain.setBounds(90, 23, 69, 27);
		
		Button operateCopyEncoded = new Button(operateGroup, SWT.NONE);
		operateCopyEncoded.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-copy-16.png"));
		operateCopyEncoded.setText("Encode");
		operateCopyEncoded.setBounds(165, 23, 74, 27);
		operateGroup.getBounds().height = 30;
		
		Group encodedGroup = new Group(composite, SWT.NONE);
		encodedGroup.setBounds(10, 255, 558, 175);
		encodedGroup.setText("Base64");
		
		Composite encodedComposite = new Composite(encodedGroup, SWT.NONE);
		encodedComposite.setBounds(10, 20, 538, 145);
		
		final Text encodedText = new Text(encodedComposite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		encodedText.setBounds(0, 0, 538, 145);
		
		operateClean.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				plainText.setText("");
			}
		});

		operateCopyPlain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNotBlank(plainText.getText())) {
					clipboard.setContents(new Object[] { plainText.getText() }, new TextTransfer[] { textTransfer });
				} else {
					clipboard.clearContents();
				}
			}
		});
		
		operateCopyEncoded.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNotBlank(encodedText.getText())) {
					clipboard.setContents(new Object[] { encodedText.getText() }, new TextTransfer[] { textTransfer });
				} else {
					clipboard.clearContents();
				}
			}
		});
		
		plainText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				if (BASE64_PLAIN_MODIFY_FLAG) {
					BASE64_CURRENT_MODIFY_PLAIN = true;
					BASE64_ENCODED_MODIFY_FLAG = false;
					String plain = plainText.getText();
					encodedText.setText(Base64Utils.bytesToBase64(plain.getBytes(BASE64_CHARSET)));
					BASE64_ENCODED_MODIFY_FLAG = true;
				}
			}
		});
		
		encodedText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				if (BASE64_ENCODED_MODIFY_FLAG) {
					BASE64_CURRENT_MODIFY_PLAIN = false;
					BASE64_PLAIN_MODIFY_FLAG = false;
					String encoded = encodedText.getText();
					plainText.setText(new String(Base64Utils.base64ToBytes(encoded), BASE64_CHARSET));
					BASE64_PLAIN_MODIFY_FLAG = true;
				}
			}
		});

		charsetRadioUTF8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BASE64_CHARSET = CharsetUtils.UTF_8;
				if (BASE64_CURRENT_MODIFY_PLAIN) {
					BASE64_ENCODED_MODIFY_FLAG = false;
					String plain = plainText.getText();
					encodedText.setText(Base64Utils.bytesToBase64(plain.getBytes(BASE64_CHARSET)));
					BASE64_ENCODED_MODIFY_FLAG = true;
				} else {
					BASE64_PLAIN_MODIFY_FLAG = false;
					String encoded = encodedText.getText();
					plainText.setText(new String(Base64Utils.base64ToBytes(encoded), BASE64_CHARSET));
					BASE64_PLAIN_MODIFY_FLAG = true;
				}
			}
		});
		
		charsetRadioGBK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BASE64_CHARSET = CharsetUtils.GBK;
				if (BASE64_CURRENT_MODIFY_PLAIN) {
					BASE64_ENCODED_MODIFY_FLAG = false;
					String plain = plainText.getText();
					encodedText.setText(Base64Utils.bytesToBase64(plain.getBytes(BASE64_CHARSET)));
					BASE64_ENCODED_MODIFY_FLAG = true;
				} else {
					BASE64_PLAIN_MODIFY_FLAG = false;
					String encoded = encodedText.getText();
					plainText.setText(new String(Base64Utils.base64ToBytes(encoded), BASE64_CHARSET));
					BASE64_PLAIN_MODIFY_FLAG = true;
				}
			}
		});

		charsetRadioGB2312.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BASE64_CHARSET = CharsetUtils.GB2312;
				if (BASE64_CURRENT_MODIFY_PLAIN) {
					BASE64_ENCODED_MODIFY_FLAG = false;
					String plain = plainText.getText();
					encodedText.setText(Base64Utils.bytesToBase64(plain.getBytes(BASE64_CHARSET)));
					BASE64_ENCODED_MODIFY_FLAG = true;
				} else {
					BASE64_PLAIN_MODIFY_FLAG = false;
					String encoded = encodedText.getText();
					plainText.setText(new String(Base64Utils.base64ToBytes(encoded), BASE64_CHARSET));
					BASE64_PLAIN_MODIFY_FLAG = true;
				}
			}
		});
	}
}
