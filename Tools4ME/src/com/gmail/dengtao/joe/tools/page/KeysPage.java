package com.gmail.dengtao.joe.tools.page;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.gmail.dengtao.joe.commons.util.RandomUtils;
import com.gmail.dengtao.joe.commons.util.StringUtils;
import com.gmail.dengtao.joe.tools.Window;

public class KeysPage implements TabPage {

	private Clipboard clipboard;	// 
	private TextTransfer textTransfer = TextTransfer.getInstance();
	
	private int START_RANDOM_MODEN = 0;
	private Set<char[]> RANDOM_CHARS_SET = new HashSet<char[]>();
	private char[] RANDOM_CHARS_NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private char[] RANDOM_CHARS_LETTER_LOWER = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	private char[] RANDOM_CHARS_LETTER_UPPER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private char[] RANDOM_CHARS_UNDERLINE = {'_'};

	@Override
	public void create(CTabFolder folder) {
		clipboard = new Clipboard(folder.getDisplay());
		
		CTabItem tbtmPassword = new CTabItem(folder, SWT.NONE);
		tbtmPassword.setText("Key");
		tbtmPassword.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-password-16.png"));
		
		Composite composite = new Composite(folder, SWT.NONE);
		tbtmPassword.setControl(composite);
		
		Group contentGroup = new Group(composite, SWT.NONE);
		contentGroup.setText("Contain");
		contentGroup.setBounds(10, 10, 273, 119);
		
		final Button incNumber = new Button(contentGroup, SWT.CHECK);
		incNumber.setSelection(true);
		incNumber.setBounds(10, 22, 98, 17);
		incNumber.setText("Number");
		RANDOM_CHARS_SET.add(RANDOM_CHARS_NUMBER);
		
		final Button incLetterLower = new Button(contentGroup, SWT.CHECK);
		incLetterLower.setBounds(10, 68, 98, 17);
		incLetterLower.setText("letters");
		
		final Button incLetterUpper = new Button(contentGroup, SWT.CHECK);
		incLetterUpper.setBounds(10, 92, 98, 17);
		incLetterUpper.setText("LETTERS");
		
		final Button incUnderline = new Button(contentGroup, SWT.CHECK);
		incUnderline.setBounds(10, 45, 98, 17);
		incUnderline.setText("underline");
		
		Group starterGroup = new Group(composite, SWT.NONE);
		starterGroup.setText("StartWith");
		starterGroup.setBounds(289, 10, 280, 119);
		
		Button startRandom = new Button(starterGroup, SWT.RADIO);
		startRandom.setText("random");
		startRandom.setSelection(true);
		startRandom.setBounds(10, 23, 117, 17);
		
		Button startNum = new Button(starterGroup, SWT.RADIO);
		startNum.setText("number");
		startNum.setBounds(146, 23, 117, 17);
		
		Button startLetterLower = new Button(starterGroup, SWT.RADIO);
		startLetterLower.setText("letters");
		startLetterLower.setBounds(10, 91, 117, 17);
		
		Button startLetterUpper = new Button(starterGroup, SWT.RADIO);
		startLetterUpper.setText("LETTERS");
		startLetterUpper.setBounds(10, 68, 117, 17);
		
		Button startUnderline = new Button(starterGroup, SWT.RADIO);
		startUnderline.setText("underline");
		startUnderline.setBounds(10, 45, 117, 17);
		
		Group lengthGroup = new Group(composite, SWT.NONE);
		lengthGroup.setText("Length");
		lengthGroup.setBounds(10, 135, 177, 55);
		
		final Spinner lengthSpinner = new Spinner(lengthGroup, SWT.BORDER);
		lengthSpinner.setMaximum(1024);
		lengthSpinner.setMinimum(1);
		lengthSpinner.setIncrement(10);
		lengthSpinner.setSelection(6);
		lengthSpinner.setBounds(10, 22, 61, 23);
		
		Label lengthDesc = new Label(lengthGroup, SWT.NONE);
		lengthDesc.setBounds(77, 25, 81, 17);
		lengthDesc.setText("Range:1-1024");
		
		Group countGroup = new Group(composite, SWT.NONE);
		countGroup.setBounds(193, 135, 90, 55);
		countGroup.setText("Count");
		
		final Spinner genCount = new Spinner(countGroup, SWT.BORDER);
		genCount.setMaximum(10240);
		genCount.setMinimum(1);
		genCount.setIncrement(1);
		genCount.setSelection(3);
		genCount.setBounds(10, 22, 69, 23);
		
		Group operateGroup = new Group(composite, SWT.NONE);
		operateGroup.setText("Action");
		operateGroup.setBounds(289, 135, 280, 55);
		
		final Button save = new Button(operateGroup, SWT.NONE);
		save.setEnabled(false);
		save.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-save-16.png"));
		save.setBounds(78, 18, 60, 27);
		save.setText("Save");
		
		final Button copy = new Button(operateGroup, SWT.NONE);
		copy.setEnabled(false);
		copy.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-copy-16.png"));
		copy.setText("Copy");
		copy.setBounds(144, 18, 60, 27);
		
		final Button gen = new Button(operateGroup, SWT.NONE);
		gen.setText("Make");
		gen.setImage(SWTResourceManager.getImage(Window.class, "/icons/icon-run-16.png"));
		gen.setBounds(210, 18, 60, 27);
		
		final Button clean = new Button(operateGroup, SWT.NONE);
		clean.setEnabled(false);
		clean.setText("Clean");
		clean.setImage(SWTResourceManager.getImage(KeysPage.class, "/icons/icon-delete-16.png"));
		clean.setBounds(10, 18, 60, 27);
		
		Group resultGroup = new Group(composite, SWT.NONE);
		resultGroup.setText("Result");
		resultGroup.setBounds(10, 196, 562, 237);
		
		final Text resultText = new Text(resultGroup, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		resultText.setBounds(10, 20, 542, 207);
		
		incNumber.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (incNumber.getSelection()) {
					RANDOM_CHARS_SET.add(RANDOM_CHARS_NUMBER);
				} else {
					RANDOM_CHARS_SET.remove(RANDOM_CHARS_NUMBER);
				}
			}
		});
		
		incLetterLower.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (incLetterLower.getSelection()) {
					RANDOM_CHARS_SET.add(RANDOM_CHARS_LETTER_LOWER);
				} else {
					RANDOM_CHARS_SET.remove(RANDOM_CHARS_LETTER_LOWER);
				}
			}
		});
		
		incLetterUpper.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (incLetterUpper.getSelection()) {
					RANDOM_CHARS_SET.add(RANDOM_CHARS_LETTER_UPPER);
				} else {
					RANDOM_CHARS_SET.remove(RANDOM_CHARS_LETTER_UPPER);
				}
			}
		});
		
		incUnderline.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (incUnderline.getSelection()) {
					RANDOM_CHARS_SET.add(RANDOM_CHARS_UNDERLINE);
				} else {
					RANDOM_CHARS_SET.remove(RANDOM_CHARS_UNDERLINE);
				}
			}
		});
		
		clean.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				copy.setEnabled(false);
				save.setEnabled(false);
				clean.setEnabled(false);
				resultText.setText("");
			}
		});
		
		save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				String result = resultText.getText();
				if (StringUtils.isBlank(result)) {
					MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
					messageBox.setText("异常");
			        messageBox.setMessage("尚未生成任何结果！");
			        messageBox.open();
					return;
				}
				FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				dialog.setFilterNames(new String[]{"普通文本 *.txt", "所有文件 *.*"});
				dialog.setFilterExtensions(new String[]{ "*.txt", "*.*" });
				
				String path = dialog.open();
				if (StringUtils.isNotBlank(path)) {
					OutputStream os = null;
					try {
						os = new FileOutputStream(new File(path));
						os.write(result.getBytes());
						os.flush();
					} catch (Exception e) {
						MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
						messageBox.setText("异常");
				        messageBox.setMessage("保存出现异常：" + e.getLocalizedMessage());
				        messageBox.open();
					} finally {
						if (os != null) try { os.close(); } catch (IOException e) { }
					}
				}
			}
		});
		
		copy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isNotBlank(resultText.getText())) {
					clipboard.setContents(new Object[] { resultText.getText() }, new TextTransfer[] { textTransfer });
				} else {
					clipboard.clearContents();
				}
			}
		});
		
		gen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int count = genCount.getSelection();
				int length = lengthSpinner.getSelection();
				int mode = START_RANDOM_MODEN;
				
				StringBuffer charsSb = new StringBuffer();
				for (char[] letters : RANDOM_CHARS_SET) {
					for (char c : letters) charsSb.append(c);
				}
				String randomChars = charsSb.toString();
				if (StringUtils.isBlank(randomChars)) {
					MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
					messageBox.setText("警告");
			        messageBox.setMessage("请选择至少一种类别数据作为内容！");
			        messageBox.open();
					return;
				}
				
				StringBuffer prefixSb = new StringBuffer();
				if (mode == 1) {
					if (incNumber.getSelection()) {
						for (char c : RANDOM_CHARS_NUMBER) {
							prefixSb.append(c);
						}
					}
				} else if (mode == 2) {
					if (incUnderline.getSelection()) {
						for (char c : RANDOM_CHARS_UNDERLINE) {
							prefixSb.append(c);
						}
					}
				} else if (mode == 3) {
					if (incLetterLower.getSelection()) {
						for (char c : RANDOM_CHARS_LETTER_LOWER) {
							prefixSb.append(c);
						}
					}
				} else if (mode == 4) {
					if (incLetterUpper.getSelection()) {
						for (char c : RANDOM_CHARS_LETTER_UPPER) {
							prefixSb.append(c);
						}
					}
				} else {
					if (incNumber.getSelection()) {
						for (char c : RANDOM_CHARS_NUMBER) {
							prefixSb.append(c);
						}
					}
					if (incUnderline.getSelection()) {
						for (char c : RANDOM_CHARS_UNDERLINE) {
							prefixSb.append(c);
						}
					}
					if (incLetterLower.getSelection()) {
						for (char c : RANDOM_CHARS_LETTER_LOWER) {
							prefixSb.append(c);
						}
					}
					if (incLetterUpper.getSelection()) {
						for (char c : RANDOM_CHARS_LETTER_UPPER) {
							prefixSb.append(c);
						}
					}
				}
			
				String randomPrefixChars = prefixSb.toString();
				if (StringUtils.isBlank(randomPrefixChars)) {
					randomPrefixChars = randomChars;
				}
				StringBuffer result = new StringBuffer();
				if (length > 1) {
					for (int i = 0; i < count; i++) {
						String prefix = RandomUtils.random(1, randomPrefixChars);
						result.append(prefix);
						String random = RandomUtils.random(length - 1, randomChars);
						result.append(random);
						result.append("\n");
					}
				} else {
					for (int i = 0; i < count; i++) {
						String prefix = RandomUtils.random(1, randomPrefixChars);
						result.append(prefix);
						result.append("\n");
					}
				}
				
				resultText.setText(result.toString());
				copy.setEnabled(true);
				save.setEnabled(true);
				clean.setEnabled(true);
			}
		});
		
		startRandom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				START_RANDOM_MODEN = 0;
			}
		});
		
		startNum.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				START_RANDOM_MODEN = 1;
			}
		});
		
		startUnderline.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				START_RANDOM_MODEN = 2;
			}
		});
		
		startLetterLower.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				START_RANDOM_MODEN = 3;
			}
		});

		startLetterUpper.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				START_RANDOM_MODEN = 4;
			}
		});
	}

}
