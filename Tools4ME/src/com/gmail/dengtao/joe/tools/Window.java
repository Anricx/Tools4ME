package com.gmail.dengtao.joe.tools;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.gmail.dengtao.joe.tools.page.Base64Page;
import com.gmail.dengtao.joe.tools.page.JSONPage;
import com.gmail.dengtao.joe.tools.page.KeysPage;
import com.gmail.dengtao.joe.tools.page.MD5Page;
import com.gmail.dengtao.joe.tools.page.QRCodePage;
import com.gmail.dengtao.joe.tools.page.SixBitPage;
import com.gmail.dengtao.joe.tools.page.UnicodePage;
import com.gmail.dengtao.joe.tools.page.UrlEncodePage;
import com.gmail.dengtao.joe.tools.page.XMLPage;

public class Window extends ApplicationWindow {
	
	public Window() {
		super(null);
		setShellStyle(SWT.CLOSE | SWT.MIN | SWT.TITLE);
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Tools4ME");
		shell.setSize(585, 495);
		shell.setImage(SWTResourceManager.getImage(this.getClass(), "/icons/icon-logo-256.ico"));
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		CTabFolder folder = new CTabFolder(container, SWT.BORDER | SWT.FLAT);
		folder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		// Create Base64UI
		new Base64Page().create(folder);
		// Create SixBitUI
		new SixBitPage().create(folder);
		// Create Unicode
		new UnicodePage().create(folder);
		// Create UrlEncode
		new UrlEncodePage().create(folder);
		// Create MD5
		new MD5Page().create(folder);
		// Create QRCode
		new QRCodePage().create(folder);
		// Create Keys
		new KeysPage().create(folder);
		// Create XML
		new XMLPage().create(folder);
		// Create JSON
		new JSONPage().create(folder);
		
		return container;
	}
	
	public static void main(String[] args) {
		Window window = new Window();
		// Don't return from open() until window closes
		window.setBlockOnOpen(true);
	    // Open the main window
		window.open();
	    // Dispose the display
	    Display.getCurrent().dispose();
	    // Dispose all resources
	    SWTResourceManager.dispose();
	}
}
