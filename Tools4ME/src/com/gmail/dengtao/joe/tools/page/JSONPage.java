package com.gmail.dengtao.joe.tools.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.gmail.dengtao.joe.commons.util.JSONUtils;
import com.gmail.dengtao.joe.commons.util.StringUtils;
import com.gmail.dengtao.joe.tools.Window;

public class JSONPage implements TabPage {
	
	private Clipboard clipboard;	// 
	private TextTransfer textTransfer = TextTransfer.getInstance();
	
	@Override
	public void create(CTabFolder folder) {
		clipboard = new Clipboard(folder.getDisplay());

		CTabItem tab = new CTabItem(folder, SWT.NONE);
		tab.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-json-16.png"));
		tab.setText("JSON");
		
		Composite composite = new Composite(folder, SWT.NONE);
		tab.setControl(composite);
		
		Group plainGroup = new Group(composite, SWT.NONE);
		plainGroup.setBounds(10, 10, 558, 355);
		plainGroup.setText("JSON");
		
		Composite plainComposite = new Composite(plainGroup, SWT.NONE);
		plainComposite.setBounds(10, 20, 538, 325);
		
		final Text plainText = new Text(plainComposite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		plainText.setBounds(0, 0, 538, 325);
		
		Group operateGroup = new Group(composite, SWT.NONE);
		operateGroup.setText("Action");
		operateGroup.setBounds(10, 371, 558, 62);
		
		Composite operateComposite = new Composite(operateGroup, SWT.NONE);
		operateComposite.setBounds(10, 22, 538, 30);
		
		Button pretty = new Button(operateComposite, SWT.NONE);
		pretty.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-pretty-16.png"));
		pretty.setBounds(200, 0, 80, 27);
		pretty.setText("Style");
		
		Button clean = new Button(operateComposite, SWT.NONE);
		clean.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-delete-16.png"));
		clean.setText("Clean");
		clean.setBounds(458, 0, 80, 27);
		
		Button compact = new Button(operateComposite, SWT.NONE);
		compact.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-compact-16.png"));
		compact.setText("Compa");
		compact.setBounds(286, 0, 80, 27);
		
		Button copy = new Button(operateComposite, SWT.NONE);
		copy.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-copy-16.png"));
		copy.setBounds(372, 0, 80, 27);
		copy.setText("Copy");
		
		pretty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					String pretty = JSONUtils.pretty(plainText.getText());
					plainText.setText(pretty);
				} catch (Exception e) {
					MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
					messageBox.setText("异常");
			        messageBox.setMessage("解析JSON异常：" + e.getLocalizedMessage());
			        messageBox.open();
				}
			}
		});
		
		compact.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					String compact = JSONUtils.compact(plainText.getText());
					plainText.setText(compact);
				} catch (Exception e) {
					MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
					messageBox.setText("异常");
			        messageBox.setMessage("解析JSON异常：" + e.getLocalizedMessage());
			        messageBox.open();
				}
			}
		});
		
		copy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNotBlank(plainText.getText())) {
					clipboard.setContents(new Object[] { plainText.getText() }, new TextTransfer[] { textTransfer });
				} else {
					clipboard.clearContents();
				}
			}
		});
		
		clean.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				plainText.setText("");
			}
		});
	}

}