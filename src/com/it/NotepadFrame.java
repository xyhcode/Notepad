package com.it;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.List;
import java.awt.PrintJob;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class NotepadFrame extends JFrame implements ActionListener {
	// �½��ļ������ļ��������ļ������Ϊ��ҳ�����á���ӡ���˳�
	private JMenuItem newFile, openFile, saverFile, thesaver, pageset, printFile, exit;
	// ���������С����ơ�ճ����ɾ�������������ҡ�������һ�����滻��ת����ȫѡ��ʱ��/����
	private JMenuItem repeal, shear, copy, paste, delete, search, find, findnext, replace, togo, all, date;
	// �С����塢������ɫ��������ɫ
	private JMenuItem font, fontcolor, bgcolor;
	// ״̬�����Զ�����
	private JCheckBoxMenuItem statusbar, line;
	// ���ͷ��������ڡ�����
	private JMenuItem send, about, help;
	// ��������
	public JTextArea textArea;
	// ������
	private JScrollPane scrollPane;
	// ת��togo
	JDialog jd;
	// ����������
	public UndoManager mgr = new UndoManager();

	public static String name;// ��ǰ������û���

	PrintJob p = null;// ����һ��Ҫ��ӡ�Ķ���
	Graphics g = null;// Ҫ��ӡ�Ķ���
	// ϵͳ���а�ss
	Toolkit t = Toolkit.getDefaultToolkit();
	Clipboard clip = t.getSystemClipboard();
//	repeal, shear, copy, paste, delete, search, find, findnext, replace, togo, all, date

	
	//��С
	JMenuItem shrink;
	//�Ŵ�
	JMenuItem amplify;
	//�ָ�
	JMenuItem regain;

	String oldValue;// ��ű༭��ԭ�������ݣ����ڱȽ��ı��Ƿ��иĶ�
	boolean isNewFile = true;// �Ƿ����ļ�(δ�������)
	File currentFile;// ��ǰ�ļ���
	
	int textAreasize;//�����С
	String textAreaname;//��������
	int textAreastyle;//������ʽ

	// ������Ϣ �ǡ���ȡ�� 0--�ǡ�1--��2--ȡ��
	private JPopupMenu jpm; // �Ҽ������˵�
	private JMenuItem repeal2; // ����
	private JMenuItem shear2; // ����
	private JMenuItem copy2; // ����
	private JMenuItem paste2; // ճ��
	private JMenuItem delete2; // ɾ��
	private JMenuItem all2; // ȫѡ
	private JMenuItem toLeft; // ���ҵ�����Ķ�˳��
	private JMenuItem showUnicode; // ��ʾUnicode�����ַ�
	private JMenuItem close; // �ر�IME
	private JMenuItem unicode; // ����Unicode�����ַ�
	private JMenuItem reselect; // ������ѡ
	private JMenuItem search2; // ����
	public int linenum = 1;// ��
	int columnnum = 1;// ��
	JToolBar tool;// �ײ�״̬��
	JLabel jb1, jb2;
	int length = 0;// ����

	public NotepadFrame() {
		this.setSize(1290, 835);
		this.setTitle("���±�");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon("images/notepad.png").getImage());
		JPanel p = CreatePanl();

		this.setContentPane(p);// ���
		this.setJMenuBar(cdt());// �˵���
		this.setVisible(true);// ��ʾ
	}

	private JPanel CreatePanl() {
		// TODO Auto-generated method stub
		JPanel jp = new JPanel();
//		jp.setBorder(new EmptyBorder(0, 0, 0, 0));
//		// ���ñ߿򲼾�
		jp.setLayout(new BorderLayout(0, 0));
		textArea = new JTextArea(73, 200);// ��������
		textArea.setBorder(null);// ���õ��ʱ�����������
		textArea.setWrapStyleWord(true); // ���ֳ��ȳ���һ��ʱ�Զ�����
		textArea.setLineWrap(true); // �ı��༭��Ĭ���Զ�����
		textArea.setFont(new Font("΢���ź�", Font.PLAIN, 21));// �����С
		textAreasize=textArea.getFont().getSize();
		textAreaname=textArea.getFont().getFamily();
		textAreastyle=textArea.getFont().getStyle();
		
		oldValue = textArea.getText();// ��ȡԭ�ı��༭��������
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);// ������
		jpm = new JPopupMenu();// �Ҽ��Ĳ˵���
		addPopup(textArea, jpm);
//		repeal, shear, copy, paste, delete, search, find, findnext, replace, togo, all, date
		repeal2 = new JMenuItem("����(U)");
		repeal2.addActionListener(this);
		jpm.add(repeal2);
		jpm.addSeparator();
		shear2 = new JMenuItem("����(T)");
		shear2.addActionListener(this);
		jpm.add(shear2);
		copy2 = new JMenuItem("����(C)");
		copy2.addActionListener(this);
		jpm.add(copy2);
		paste2 = new JMenuItem("ճ��(P)");
		paste2.addActionListener(this);
		jpm.add(paste2);
		delete2 = new JMenuItem("ɾ��(D)");
		delete2.addActionListener(this);
		jpm.add(delete2);
		jpm.addSeparator();
		all2 = new JMenuItem("ȫѡ(A)");
		all2.addActionListener(this);
		jpm.add(all2);
		jpm.addSeparator();
		toLeft = new JMenuItem("���ҵ�����Ķ�˳��(R)");
		toLeft.addActionListener(this);
		jpm.add(toLeft);
		showUnicode = new JMenuItem("��ʾUnicode�����ַ�(S)");
		showUnicode.setVisible(true);
		Color c = new Color(253, 253, 253);
		showUnicode.addActionListener(this);
		jpm.add(showUnicode);
		unicode = new JMenuItem("����Unicode�����ַ�(I)");
		
		unicode.addActionListener(this);
		jpm.add(unicode);
		jpm.addSeparator();
		close = new JMenuItem("�ر����뷨(L)");
		close.addActionListener(this);
		jpm.add(close);
		reselect = new JMenuItem("������ѡ(R)");
		reselect.addActionListener(this);
		jpm.add(reselect);
		jpm.addSeparator();
		search2 = new JMenuItem("ʹ��sogou����");
		search2.addActionListener(this);
		jpm.add(search2);
		jp.add(scrollPane, BorderLayout.CENTER);
		// ����״̬��
		tool = new JToolBar();
		tool.setSize(textArea.getSize().width, 10);// toolState.setLayout(new FlowLayout(FlowLayout.LEFT));
		jb1 = new JLabel("    �� " + linenum + " ��, �� " + columnnum + " ��  ");
		tool.add(jb1); // �����������
		tool.addSeparator();

		jb2 = new JLabel("    һ�� " + length + " ��  ");
		tool.add(jb2); // �������ͳ��
		tool.setFloatable(false);
		textArea.addCaretListener(new CaretListener() { // ��¼����������
			public void caretUpdate(CaretEvent e) {
				// sum=0;
				JTextArea editArea = (JTextArea) e.getSource();
				try {
					int caretpos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretpos);
					columnnum = caretpos - textArea.getLineStartOffset(linenum);
					linenum += 1;
					jb1.setText("    �� " + linenum + " ��, �� " + (columnnum + 1) + " ��  ");
					// sum+=columnnum+1;
					// length+=sum;
					length = NotepadFrame.this.textArea.getText().toString().length();
					jb2.setText("    һ�� " + length + " ��  ");
				} catch (Exception ex) {

				}
			}
		});
		jp.add(tool, BorderLayout.SOUTH);// �����ӹ�����
//	��	final JPopupMenu jg = new JPopupMenu(); // ��������ʽ�˵������������ǲ˵���
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)// ֻ��Ӧ����Ҽ������¼�
				{
					// �˵�
					jpm.show(e.getComponent(), e.getX(), e.getY());// �����λ����ʾ����ʽ�˵�
				}
			}
		});
		// �����ļ���
		textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				// TODO Auto-generated method stub
				mgr.addEdit(e.getEdit());
			}

		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitWindowChoose();
			}
		});
		return jp;// �������
	}

	// ��ʾ�Ҽ� �˵�
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	// �����˵�

	private JMenuBar cdt() {// �˵���
//		newFile,openFile,saverFile,thesaver,pageset,printFile,exit;
		JMenuBar jb = new JMenuBar();
		JMenu jm = new JMenu("�ļ�(F)");
		jm.setFont(new Font("����", Font.BOLD, 20));
		newFile = new JMenuItem("�½�(N) ");
		newFile.setFont(new Font("����", Font.BOLD, 20));
		// ���ÿ�ݼ�
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		newFile.addActionListener(this);

		openFile = new JMenuItem("��(O) ");
		openFile.setFont(new Font("����", Font.BOLD, 20));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		openFile.addActionListener(this);

		saverFile = new JMenuItem("����(S) ");
		saverFile.setFont(new Font("����", Font.BOLD, 20));
		saverFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		saverFile.addActionListener(this);

		thesaver = new JMenuItem("���Ϊ(A) ");
		thesaver.setFont(new Font("����", Font.BOLD, 20));
		thesaver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_MASK));
		thesaver.addActionListener(this);

		pageset = new JMenuItem("ҳ������(U) ");
		pageset.setFont(new Font("����", Font.BOLD, 20));
		pageset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
		pageset.addActionListener(this);

		printFile = new JMenuItem("��ӡ(P) ");
		printFile.setFont(new Font("����", Font.BOLD, 20));
		printFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		printFile.addActionListener(this);
		exit = new JMenuItem("�˳�(X) ");
		exit.setFont(new Font("����", Font.BOLD, 20));
		exit.addActionListener(this);
		// �¼�����

		jm.add(newFile);
		jm.add(openFile);
		jm.add(saverFile);
		jm.add(thesaver);
		jm.addSeparator();// ����
		jm.add(pageset);
		jm.add(printFile);
		jm.addSeparator();
		jm.add(exit);

		JMenu jm2 = new JMenu("�༭(E)");
		jm2.setFont(new Font("����", Font.BOLD, 20));
		repeal = new JMenuItem("����(U) ");
		repeal.setFont(new Font("����", Font.BOLD, 20));
		repeal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		repeal.addActionListener(this);

		shear = new JMenuItem("����(T) ");
		shear.setFont(new Font("����", Font.BOLD, 20));
		shear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		shear.addActionListener(this);

		copy = new JMenuItem("����(C) ");
		copy.setFont(new Font("����", Font.BOLD, 20));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		copy.addActionListener(this);

		paste = new JMenuItem("ճ��(P) ");
		paste.setFont(new Font("����", Font.BOLD, 20));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		paste.addActionListener(this);

		delete = new JMenuItem("ɾ��(L) ");
		delete.setFont(new Font("����", Font.BOLD, 20));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_MASK));
		delete.addActionListener(this);

		search = new JMenuItem("ʹ��sogou����(T) ");
		search.setFont(new Font("����", Font.BOLD, 20));
		search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
		search.addActionListener(this);

		find = new JMenuItem("����(F) ");
		find.setFont(new Font("����", Font.BOLD, 20));
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
		find.addActionListener(this);

		findnext = new JMenuItem("������һ��(N) ");
		findnext.setFont(new Font("����", Font.BOLD, 20));
		findnext.setAccelerator(KeyStroke.getKeyStroke("F3"));
		findnext.addActionListener(this);

		replace = new JMenuItem("�滻(R) ");
		replace.setFont(new Font("����", Font.BOLD, 20));
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
		replace.addActionListener(this);

		togo = new JMenuItem("ת��(G) ");
		togo.setFont(new Font("����", Font.BOLD, 20));
		togo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_MASK));
		togo.addActionListener(this);

		all = new JMenuItem("ȫѡ(A) ");
		all.setFont(new Font("����", Font.BOLD, 20));
		all.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
		all.addActionListener(this);

		date = new JMenuItem("ʱ��/����(D) ");
		date.setFont(new Font("����", Font.BOLD, 20));
		date.setAccelerator(KeyStroke.getKeyStroke("F5"));
		date.addActionListener(this);

//		 repeal,shear,copy,paste,delete,search,find,findnext,replace,togo,all,date
		jm2.add(repeal);
		jm2.addSeparator();
		jm2.add(shear);
		jm2.add(copy);
		jm2.add(paste);
		jm2.add(delete);
		jm2.addSeparator();
		jm2.add(search);
		jm2.add(find);
		jm2.add(findnext);
		jm2.add(replace);
		jm2.add(togo);
		jm2.addSeparator();
		jm2.add(all);
		jm2.add(date);

//		line,font;
		JMenu jm3 = new JMenu("��ʽ(O)");
		jm3.setFont(new Font("����", Font.BOLD, 20));
		line = new JCheckBoxMenuItem("�Զ�����(W)", true);
		line.setFont(new Font("����", Font.BOLD, 20));
		line.addActionListener(this);

		font = new JMenuItem("����(F)");
		font.setFont(new Font("����", Font.BOLD, 20));
		font.addActionListener(this);

		fontcolor = new JMenuItem("������ɫ(C)");
		fontcolor.setFont(new Font("����", Font.BOLD, 20));
		fontcolor.addActionListener(this);

		bgcolor = new JMenuItem("������ɫ(B)");
		bgcolor.setFont(new Font("����", Font.BOLD, 20));
		bgcolor.addActionListener(this);
		jm3.add(line);
//		jm3.addSeparator();
		jm3.add(font);
//		jm3.addSeparator();
		jm3.add(fontcolor);
//		jm3.addSeparator();
		jm3.add(bgcolor);
//		statusbar
		JMenu jm4 = new JMenu("�鿴(V)");
		jm4.setFont(new Font("����", Font.BOLD, 20));
		JMenu zoom = new JMenu("����(Z)");
		zoom.setFont(new Font("����", Font.BOLD, 20));
		amplify=new JMenuItem ("�Ŵ�");
		amplify.setFont(new Font("����", Font.BOLD, 20));
		amplify.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_MASK));
		shrink =new JMenuItem("��С");
		shrink.setFont(new Font("����", Font.BOLD, 20));
		shrink.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_MASK));
		regain =new JMenuItem("�ָ�Ĭ������");
		regain.setFont(new Font("����", Font.BOLD, 20));
		regain.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_MASK));
		statusbar = new JCheckBoxMenuItem("״̬��(S)", true);
		statusbar.setFont(new Font("����", Font.BOLD, 20));
		statusbar.addActionListener(this);
		amplify.addActionListener(this);
		shrink.addActionListener(this);
		regain.addActionListener(this);
		zoom.add(amplify);
		zoom.add(shrink);
		zoom.add(regain);
		jm4.add(zoom);
		jm4.add(statusbar);

		JMenu jm5 = new JMenu("����(H)");
		jm5.setFont(new Font("����", Font.BOLD, 20));
		help = new JMenuItem("�鿴����(H)");
		help.setFont(new Font("����", Font.BOLD, 20));
		help.addActionListener(this);

		send = new JMenuItem("���ͷ���(F)");
		send.setFont(new Font("����", Font.BOLD, 20));
		send.addActionListener(this);

		about = new JMenuItem("���ڼ��±�(A)");
		about.setFont(new Font("����", Font.BOLD, 20));
		about.addActionListener(this);

		jm5.add(help);
//		jm5.addSeparator();
		jm5.add(send);
		jm5.addSeparator();
		jm5.add(about);

		jb.add(jm);
		jb.add(jm2);
		jb.add(jm3);
		jb.add(jm4);
		jb.add(jm5);
		return jb;
	}

	// �¼�
//	repeal, shear, copy, paste, delete, search, find, findnext, replace, togo, all, date
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == newFile) {
			// �½�
			newfile();
		} else if (e.getSource() == pageset) {
			// ҳ������
			PageFormat pf = new PageFormat();// ����ҳ���С�ͷ���
			PrinterJob.getPrinterJob().pageDialog(pf);// ���ƴ�ӡ
		} else if (e.getSource() == openFile) {
			// ��
			open();
		} else if (e.getSource() == saverFile) {
			// ����
			save();
		} else if (e.getSource() == thesaver) {
			// ���Ϊ
			saveAs();
		} else if (e.getSource() == printFile) {
			// ��ӡ�ļ�
			print();
		} else if (e.getSource() == exit) {
			// �˳�
			exit();
		} else if (e.getSource() == repeal || e.getSource() == repeal2) {
			// ����
			if (mgr.canUndo()) {
				mgr.undo();
			}
		} else if (e.getSource() == shear || e.getSource() == shear2) {
			// ����
			shearv();
		} else if (e.getSource() == copy || e.getSource() == copy2) {
			// ����
			copyv();
		} else if (e.getSource() == paste || e.getSource() == paste2) {
			// ճ��
			pastev();
		} else if (e.getSource() == delete || e.getSource() == delete2) {
			// ɾ��
			deletev();
		} else if (e.getSource() == search || e.getSource() == search2) {
			// ����
			searchv();
		} else if (e.getSource() == line) {
			// �Զ�����
			if (line.getState()) {
				textArea.setLineWrap(true);// ��ʾ
			} else {
				textArea.setLineWrap(false);// ����
			}
		} else if (e.getSource() == statusbar) {
			// �ײ�״̬����ʾ����״̬
			if (statusbar.getState()) {
				tool.setVisible(true);// ��ʾ
			} else {
				tool.setVisible(false);// ����
			}
		} else if (e.getSource() == all || e.getSource() == all2) {
			// ȫѡ
			allv();
		} else if (e.getSource() == date) {
			// ʱ������
			time();
		} else if (e.getSource() == togo) {
			// ת��
			togo();
		} else if (e.getSource() == help) {
			// �鿴���� ��ת�����±�����
			try {
				java.awt.Desktop.getDesktop().browse(new java.net.URI(
						"https://cn.bing.com/search?q=%E8%8E%B7%E5%8F%96%E6%9C%89%E5%85%B3+windows+10+%E4%B8%AD%E7%9A%84%E8%AE%B0%E4%BA%8B%E6%9C%AC%E7%9A%84%E5%B8%AE%E5%8A%A9&filters=guid:%224466414-zh-hans-dia%22%20lang:%22zh-hans%22&form=T00032&ocid=HelpPane-BingIA"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == send) {
			// ���ͷ���
			try {
				// �Զ���QQ
				java.awt.Desktop.getDesktop()
						.browse(new java.net.URI("http://wpa.qq.com/msgrd?v=3&uin=2730023435&site=qq&menu=yes"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == about) {
			// ����
			name = System.getProperty("user.name");// ��ȡ��ǰ�û���
			Aboutnodepad ab = new Aboutnodepad();
		} else if (e.getSource() == font) {
			// ����
			Fontv font = new Fontv(textArea.getFont());
			int returnValue = font.showFontDialog(this);
			if (returnValue == font.APPROVE_OPTION) {
				Font f = font.getSelectFont();
				textArea.setFont(f);// ��������
			} else {
				return;
			}
		} else if (e.getSource() == fontcolor) {
			// ������ɫѡ����
			Color color = JColorChooser.showDialog(this, "������ɫѡ��", Color.BLACK);
			textArea.setForeground(color);// ����������ɫ
		} else if (e.getSource() == bgcolor) {
			// ������ɫѡ��
			Color color = JColorChooser.showDialog(this, "������ɫѡ��", Color.BLACK);
			textArea.setBackground(color);// ���ñ�����ɫ
		} else if (e.getSource() == reselect) {
			// ������ѡ
			rselect();
		} else if (e.getSource() == close) {
			// �ر����뷨
			Robot s1 = null;// �Զ��������� �������
			try {
				s1 = new Robot();
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			s1.keyPress(KeyEvent.VK_SHIFT);// ����shift��
			s1.keyRelease(KeyEvent.VK_SHIFT);// �ͷ�shift��
		} else if (e.getSource() == toLeft) {
			// ���Ҷ���
		} else if (e.getSource() == showUnicode) {
			// ��ʾunicode�ַ�
		} else if (e.getSource() == unicode) {
			// ����unicode�ַ�
		} else if (e.getSource() == find) {
			// ����
			findv();
		} else if (e.getSource() == findnext) {
			// ������һ��
			findnextv();
		} else if (e.getSource() == replace) {
			// �滻
			replacev();
		}else if(e.getSource()==amplify) {
			//�Ŵ�
			int b=textArea.getFont().getSize();//�õ�����Ĵ�С
			String fontname=textArea.getFont().getFamily();//�õ����������
			int h=textArea.getFont().getStyle();//�õ�������ʽ
			textArea.setFont(new Font(fontname,h,b+20));//ÿ��20
		}else if(e.getSource()==shrink) {
			//��С
			int b=textArea.getFont().getSize();//�õ�����Ĵ�С
			String fontname=textArea.getFont().getFamily();//�õ���������
			int h=textArea.getFont().getStyle();//�õ�������ʽ
			if(b==textAreasize) {//�����ǰ�ĵ���֮ǰ�õ��ľ�����Ϊԭ������
				textArea.setFont(new Font(textAreaname,textAreastyle,textAreasize));
			}else{
				textArea.setFont(new Font(fontname,h,b-20));//ÿ�μ�20
			}
		}else if(e.getSource()==regain) {
			//�ָ�Ĭ������
			textArea.setFont(new Font(textAreaname,textAreastyle,textAreasize));
		}
	}

	public void newfile() {
		// �½�
		textArea.requestFocus();
		String currentValue = textArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue));
		if (isTextChange==false) {
			int saveChoose = JOptionPane.showConfirmDialog(this, "�ļ���δ���棬�Ƿ񱣴棿", "��ʾ",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (saveChoose == JOptionPane.YES_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("ȷ��");
				fileChooser.setDialogTitle("���Ϊ");
				int result = fileChooser.showSaveDialog(this);
				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				File saveFileName = fileChooser.getSelectedFile();
				if (saveFileName == null || saveFileName.getName().equals("")) {
					JOptionPane.showMessageDialog(this, "�ļ�������Ϊ�գ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						FileWriter fw = new FileWriter(saveFileName);
						BufferedWriter bfw = new BufferedWriter(fw);
						bfw.write(textArea.getText(), 0, textArea.getText().length());
						bfw.flush();// ˢ�¸����Ļ���
						bfw.close();
						isNewFile = false;
						currentFile = saveFileName;
						oldValue = textArea.getText();
						this.setTitle(saveFileName.getAbsolutePath());
						
					} catch (IOException ioException) {
						
					}
				}
			} else if (saveChoose == JOptionPane.NO_OPTION) {
				textArea.replaceRange("", 0, textArea.getText().length());
				this.setTitle("���±�");
				isNewFile = true;
				mgr.discardAllEdits(); // �������е�"����"����
				oldValue = textArea.getText();
			} else if (saveChoose == JOptionPane.CANCEL_OPTION) {
				return;
			}
		} else {
			textArea.replaceRange("", 0, textArea.getText().length());
			this.setTitle("���±�");
			isNewFile = true;
			mgr.discardAllEdits();// �������е�"����"����
			oldValue = textArea.getText();
		}
	}

	public void open() {
		// ��
		textArea.requestFocus();
		String currentValue = textArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue));
		if (isTextChange==false) {
			int saveChoose = JOptionPane.showConfirmDialog(this, "�ļ���δ���棬�Ƿ񱣴棿", "��ʾ",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (saveChoose == JOptionPane.YES_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("ȷ��");
				fileChooser.setDialogTitle("���Ϊ");
				int result = fileChooser.showSaveDialog(this);
				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				File saveFileName = fileChooser.getSelectedFile();
				if (saveFileName == null || saveFileName.getName().equals("")) {
					JOptionPane.showMessageDialog(this, "�ļ�������Ϊ�գ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						FileWriter fw = new FileWriter(saveFileName);
						BufferedWriter bfw = new BufferedWriter(fw);
						bfw.write(textArea.getText(), 0, textArea.getText().length());
						bfw.flush();// ˢ�¸����Ļ���
						bfw.close();
						isNewFile = false;
						currentFile = saveFileName;
						oldValue = textArea.getText();
						this.setTitle(saveFileName.getAbsolutePath());
					} catch (IOException ioException) {
					}
				}
			} else if (saveChoose == JOptionPane.NO_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("ȷ��");
				fileChooser.setDialogTitle("���ļ�");
				int result = fileChooser.showOpenDialog(this);
				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				File fileName = fileChooser.getSelectedFile();
				if (fileName == null || fileName.getName().equals("")) {
					JOptionPane.showMessageDialog(this, "�ļ�������Ϊ�գ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						FileReader fr = new FileReader(fileName);
						BufferedReader bfr = new BufferedReader(fr);
						textArea.setText("");
						while ((str = bfr.readLine()) != null) {
							textArea.append(str+"\n");
						}
						this.setTitle(fileName.getAbsolutePath());
						fr.close();
						isNewFile = false;
						currentFile = fileName;
						oldValue = textArea.getText();
					} catch (IOException ioException) {
					}
				}
			} else {
				return;
			}
		} else {
			String str = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// fileChooser.setApproveButtonText("ȷ��");
			fileChooser.setDialogTitle("���ļ�");
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				return;
			}
			File fileName = fileChooser.getSelectedFile();
			if (fileName == null || fileName.getName().equals("")) {
				JOptionPane.showMessageDialog(this, "�ļ�������Ϊ�գ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					FileReader fr = new FileReader(fileName);
					BufferedReader bfr = new BufferedReader(fr);
					textArea.setText("");
					while ((str = bfr.readLine()) != null) {
						textArea.append(str+"\n");
					}
					this.setTitle(fileName.getAbsolutePath());
					fr.close();
					isNewFile = false;
					currentFile = fileName;
					oldValue = textArea.getText();
				} catch (IOException ioException) {
				}
			}
		}
	}

	public void save() {
		// ����
		textArea.requestFocus();
		if (isNewFile) {
			String str = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// fileChooser.setApproveButtonText("ȷ��");
			fileChooser.setDialogTitle("����");
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				return;
			}
			File saveFileName = fileChooser.getSelectedFile();
			if (saveFileName == null || saveFileName.getName().equals("")) {
				JOptionPane.showMessageDialog(this, "�ļ�������Ϊ�գ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					FileWriter fw = new FileWriter(saveFileName);
					BufferedWriter bfw = new BufferedWriter(fw);
					bfw.write(textArea.getText(), 0, textArea.getText().length());
					bfw.flush();// ˢ�¸����Ļ���
					bfw.close();
					isNewFile = false;
					currentFile = saveFileName;
					oldValue = textArea.getText();
					this.setTitle(saveFileName.getAbsolutePath());
				} catch (IOException ioException) {
				}
			}
		} else {
			try {
				FileWriter fw = new FileWriter(currentFile);
				BufferedWriter bfw = new BufferedWriter(fw);
				bfw.write(textArea.getText(), 0, textArea.getText().length());
				bfw.flush();
				fw.close();
			} catch (IOException ioException) {
			}
		}
	}

	public void saveAs() {
		// ���Ϊ
		textArea.requestFocus();
		String str = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// fileChooser.setApproveButtonText("ȷ��");
		fileChooser.setDialogTitle("���Ϊ");
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}
		File saveFileName = fileChooser.getSelectedFile();
		if (saveFileName == null || saveFileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "�ļ�������Ϊ�գ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				FileWriter fw = new FileWriter(saveFileName);
				BufferedWriter bfw = new BufferedWriter(fw);
				bfw.write(textArea.getText(), 0, textArea.getText().length());
				bfw.flush();
				fw.close();
				oldValue = textArea.getText();
				this.setTitle(saveFileName.getAbsolutePath());
			} catch (IOException ioException) {
			}
		}
	}

	public void exit() {
		// �˳�
		int exitChoose = JOptionPane.showConfirmDialog(this, "ȷ��Ҫ�˳��", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
		if (exitChoose == JOptionPane.OK_OPTION) {
			System.exit(0);
		} else {
			return;
		}
	}

//�رմ���ʱ����
	public void exitWindowChoose() {
		textArea.requestFocus();
		String currentValue = textArea.getText();
		if (currentValue.equals(oldValue) == true) {
			System.exit(0);
		} else {
			int exitChoose = JOptionPane.showConfirmDialog(this, "�ļ�δ���棬�Ƿ񱣴棿", "��ʾ", JOptionPane.YES_NO_CANCEL_OPTION);
			if (exitChoose == JOptionPane.YES_OPTION) { // boolean isSave=false;
				if (isNewFile) {
					String str = null;
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fileChooser.setApproveButtonText("ȷ��");
					fileChooser.setDialogTitle("���Ϊ");

					int result = fileChooser.showSaveDialog(this);

					if (result == JFileChooser.CANCEL_OPTION) {
						return;
					}

					File saveFileName = fileChooser.getSelectedFile();

					if (saveFileName == null || saveFileName.getName().equals("")) {
						JOptionPane.showMessageDialog(this, "�ļ�������Ϊ�գ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							FileWriter fw = new FileWriter(saveFileName);
							BufferedWriter bfw = new BufferedWriter(fw);
							bfw.write(textArea.getText(), 0, textArea.getText().length());
							bfw.flush();
							fw.close();

							isNewFile = false;
							currentFile = saveFileName;
							oldValue = textArea.getText();

							this.setTitle(saveFileName.getAbsolutePath());
							// isSave=true;
						} catch (IOException ioException) {
						}
					}
				} else {
					try {
						FileWriter fw = new FileWriter(currentFile);
						BufferedWriter bfw = new BufferedWriter(fw);
						bfw.write(textArea.getText(), 0, textArea.getText().length());
						bfw.flush();
						fw.close();
						// isSave=true;
					} catch (IOException ioException) {
					}
				}
				System.exit(0);
				// if(isSave)System.exit(0);
				// else return;
			} else if (exitChoose == JOptionPane.NO_OPTION) {
				System.exit(0);
			} else {
				return;
			}
		}
	}

	public void print() {// ��ӡ
		try {
			p = getToolkit().getPrintJob(this, "ok", null);// ����һ��Printfjob ���� p
			g = p.getGraphics();// p ��ȡһ�����ڴ�ӡ�� Graphics �Ķ���
			g.translate(1000, 800);
			textArea.printAll(g);
			p.end();// �ͷŶ��� g
		} catch (Exception a) {

		}
	}

	public void copyv() {// ����
		// ѡ���ı�
		String nr = textArea.getSelectedText();
		// �ַ���ѡ����
		StringSelection txt = new StringSelection(nr);
		clip.setContents(txt, null);
	}

	public void shearv() {// ����
		String nr = textArea.getSelectedText();
		StringSelection txt = new StringSelection(nr);
		clip.setContents(txt, null);
		int start = textArea.getSelectionStart();
		int end = textArea.getSelectionEnd();
		textArea.replaceRange("", start, end);
		textArea.requestFocus(true);
	}

	public void deletev() {
		// ɾ��
		int start = textArea.getSelectionStart();
		int end = textArea.getSelectionEnd();
		textArea.replaceRange("", start, end);
	}

	public void pastev() {
		// ճ��
		Transferable contents = clip.getContents(this); // ��ȡճ�����ϵ�����
		if (contents == null) {// �ж��ǲ���Ϊ��
			return;
		}
		String text = "";// �����޸ĵ��ı�
		try {
			text = (String) contents.getTransferData(DataFlavor.stringFlavor); // ��ʾ Java Unicode String ��
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ճ�����ݱ���ΪString���� ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
		}
		int start = textArea.getSelectionStart();// ��ȡѡ�е���ʼλ��
		int end = textArea.getSelectionEnd();// ��ȡѡ�еĽ���λ��
		textArea.replaceRange(text, start, end);// �޸��ı��������
	}

	public void searchv() {
		// sogou����
		String vg = textArea.getSelectedText();// �õ�ѡ�е�����
		String nr = textArea.getText();// �õ������ı��������
		String cf = getStringNoBlank(nr);// ȥ���ı��Ŀո�
		if (vg == null) {
			// ���û��ѡ���ı��ͰѶ����ı�������������
			if (!nr.equals("")) {// �ж��Ƿ����ı�����
				try {
					java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.sogou.com/tx?query=" + cf
							+ "&hdq=sogou-site-c91e3483cf4f9005-0001&ekv=3&ie=utf8&"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

			}

		} else {
			// ���ѡ���ı�������ѡ�е��ı�������������
			try {
				java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.sogou.com/tx?query=" + vg
						+ "&hdq=sogou-site-c91e3483cf4f9005-0001&ekv=3&ie=utf8&"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getStringNoBlank(String str) {
		// ȥ���ı������пո�
		if (str != null && !"".equals(str)) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			String strNoBlank = m.replaceAll("");
			return strNoBlank;
		} else {
			return str;
		}
	}

	public void allv() {
		// ȫѡ
		Color c = new Color(0, 120, 215);// #0078d7 red��0 g:120 blue:215
		textArea.setSelectionColor(c);
		textArea.setSelectedTextColor(Color.white);
		textArea.selectAll();
	}

	public void time() {
		// ʱ��
		int g = textArea.getSelectionEnd();
		if (g == 0) {// û��ѡ������
			// ʱ������
			Date t = new Date();// ��ǰʱ��
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");// ʱ���ʽ��
			String tiem = sd.format(t);
			textArea.insert(tiem, textArea.getCaretPosition());// д������ı� ��д���ı� �õ���ǰ����ͣ��λ�ã�
		} else {// ѡ��
			Date t = new Date();// ��ǰʱ��
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");// ʱ���ʽ��
			String tiem = sd.format(t);
			int start = textArea.getSelectionStart();
			int end = textArea.getSelectionEnd();
			textArea.replaceRange(tiem, start, end);
		}

	}
	public void togo() {
		// ת��
		final JDialog dg = new JDialog(this, "ת��ָ����", true);
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();
		JLabel jb1 = new JLabel("�к�(L)��");
		jb1.setFont(new Font("����", Font.PLAIN, 20));
		bx1.add(jb1);
		bx1.add(Box.createHorizontalStrut(234));
		Box bx2 = Box.createHorizontalBox();
		JTextField jt1 = new JTextField();
		jt1.setFont(new Font("����", Font.PLAIN, 25));
		jt1.setPreferredSize(new Dimension(320, 40));
		jt1.setMaximumSize(new Dimension(320, 40));
		jt1.setMinimumSize(new Dimension(320, 40));
		jt1.setText("1");
		jt1.selectAll();// ȫ��ѡ��
		bx2.add(Box.createHorizontalStrut(15));
		bx2.add(jt1);
		bx2.add(Box.createHorizontalStrut(10));
		Box bx3 = Box.createHorizontalBox();
		JButton jbu1 = new JButton("  ת��  ");
		jbu1.setFont(new Font("����", Font.PLAIN, 20));
		JButton jbu2 = new JButton("  ȡ��  ");
		jbu2.setFont(new Font("����", Font.PLAIN, 20));
		bx3.add(Box.createHorizontalStrut(110));
		bx3.add(jbu1);
		bx3.add(Box.createHorizontalStrut(10));
		bx3.add(jbu2);
		// �س��¼�
		jt1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyTyped(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					jbu1.doClick();// ���ȷ��
				}
			}
		});

		jbu1.addActionListener(new ActionListener() {
			// ȷ��
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int texth = textArea.getLineCount();
				int[] lineNumber = new int[texth + 1];
				String s = textArea.getText();
				int pos = 0, t = 0;

				while (true) {
					pos = s.indexOf('\12', pos);
					if (pos == -1)
						break;
					lineNumber[t++] = pos++;
				}

				int gt = 1;
				try {
					gt = Integer.parseInt(jt1.getText());
				} catch (NumberFormatException efe) {
					JOptionPane.showMessageDialog(null, "���������� ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
					jt1.requestFocus(true);
					return;
				}

				if (gt < 2 || gt >= texth) {
					if (gt < 2) {
						textArea.setCaretPosition(0);// ����λ��
					} else {
						JOptionPane.showMessageDialog(null, "������������ ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
						jt1.setText("");
						jt1.requestFocus(true);
					}
				} else {
					textArea.setCaretPosition(lineNumber[gt - 2] + 1);
				}
				dg.dispose();
			}
		});

		jbu2.addActionListener(new ActionListener() {
			// ȡ��
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dg.dispose();// �رմ���
			}
		});

		bx.add(Box.createVerticalStrut(10));
		bx.add(bx1);
		bx.add(Box.createVerticalStrut(8));
		bx.add(bx2);
		bx.add(Box.createVerticalStrut(20));
		bx.add(bx3);
		bx.add(Box.createVerticalStrut(20));
		dg.getContentPane().add(bx);// �������
		dg.setSize(380, 190);// ���ڴ�С
		dg.setLocationRelativeTo(null);// ����
		dg.setResizable(false);// ���ܸı��С
		dg.setVisible(true);// ��ʾ
	}

	public void rselect() {
		// ������ѡ
		int end = textArea.getSelectionEnd();
		int i = textArea.getCaretPosition();
		if (end == 0) {
		} else {
			textArea.setSelectionStart(-1);
			textArea.setSelectionEnd(-1);
			textArea.setCaretPosition(i);
		}
	}

	public void findv() {
		// ����
		JDialog jd = new JDialog(this, "����", true);
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();
		JLabel findnr = new JLabel("��������(N)��");
		findnr.setFont(new Font("����", Font.PLAIN, 20));
		JTextField findtxt = new JTextField();
		findtxt.setFont(new Font("����", Font.PLAIN, 20));
		findtxt.setPreferredSize(new Dimension(210, 40));
		findtxt.setMaximumSize(new Dimension(210, 40));
		findtxt.setMinimumSize(new Dimension(210, 40));
		JButton jbfinext = new JButton("������һ��(F)");
		jbfinext.setFont(new Font("����", Font.PLAIN, 20));
		bx1.add(findnr);
		bx1.add(findtxt);
		bx1.add(Box.createHorizontalStrut(30));
		bx1.add(jbfinext);

		Box bx2 = Box.createHorizontalBox();
		Box bx3 = Box.createVerticalBox();
		JCheckBox qf = new JCheckBox("���ִ�Сд(C)", true);
		qf.setFont(new Font("����", Font.PLAIN, 16));
		JCheckBox xh = new JCheckBox("ѭ��(R)", true);
		xh.setFont(new Font("����", Font.PLAIN, 16));
		bx3.add(Box.createHorizontalStrut(20));
		bx3.add(qf);
		bx3.add(xh);

		Box bx4 = Box.createVerticalBox();
		JPanel jp = new JPanel();
		JRadioButton jr1 = new JRadioButton("����(U)");
		jr1.setFont(new Font("����", Font.PLAIN, 16));
		JRadioButton jr2 = new JRadioButton("����(D)", true);
		jr2.setFont(new Font("����", Font.PLAIN, 16));
		jp.add(jr1);
		jp.add(jr2);
		ButtonGroup gr = new ButtonGroup();
		gr.add(jr1);
		gr.add(jr2);
		jp.add(jr1);
//		jp.add(Box.createVerticalStrut(20));
		jp.add(jr2);
//		jp.add(Box.createVerticalStrut(20));
		jp.setPreferredSize(new Dimension(205, 60));
		jp.setMaximumSize(new Dimension(205, 60));
		jp.setMinimumSize(new Dimension(205, 60));

		jp.setBorder(BorderFactory.createTitledBorder("����"));
		bx4.add(jp);
//		bx4.add(Box.createVerticalStrut(30));

		Box bx5 = Box.createVerticalBox();
		JButton cancel = new JButton("ȡ��");
		cancel.setFont(new Font("����", Font.PLAIN, 20));
		cancel.setPreferredSize(new Dimension(152, 43));
		cancel.setMaximumSize(new Dimension(152, 43));
		cancel.setMinimumSize(new Dimension(152, 43));
//		bx5.add(Box.createHorizontalStrut(200));
		bx5.add(Box.createVerticalStrut(10));
		bx5.add(cancel);
		bx5.add(Box.createHorizontalStrut(26));

		// ȡ��
		cancel.addActionListener(new ActionListener() {
			// ȡ��
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jd.dispose();// �رմ���
			}
		});

		// ������һ��
		jbfinext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String areastr = textArea.getText();// ��ȡ�ı����ı�
				String fieldstr = findtxt.getText();// ��ȡ�ı����ı�
				String toupparea = areastr.toUpperCase();// תΪ��д���������ִ�Сд�жϷ������
				String touppfield = fieldstr.toUpperCase();
				String A;// �������ҵ��ı�������
				String B;// �������ҵ��ı�������
				if (qf.isSelected()) {// ���ִ�Сд
					A = areastr;
					B = fieldstr;
				} else {// ȫ����Ϊ��д
					A = toupparea;
					B = touppfield;
				}
				if (findtxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "��������ҵ����ݣ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
				} else {
					int n = textArea.getCaretPosition();// ��ȡ����λ��
					int m = 0;
					if (jr1.isSelected()) {// ���ϲ���
						if (textArea.getSelectedText() == null) {
							m = A.lastIndexOf(B, n - 1);
						} else {
							m = A.lastIndexOf(B, n - findtxt.getText().length() - 1);
						}
						if (m != -1) {
							textArea.setCaretPosition(m);
							textArea.select(m, m + findtxt.getText().length());
						} else {
							if (xh.isSelected()) {// ���ѭ��
								m = A.lastIndexOf(B);// �Ӻ��濪ʼ��
								if (m != -1) {
									textArea.setCaretPosition(m);
									textArea.select(m, m + findtxt.getText().length());
								} else {
									JOptionPane.showMessageDialog(null, "�Ҳ���  " + findtxt.getText() + "��", "��ʾ",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "�Ҳ���  " + findtxt.getText() + "��", "��ʾ",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					} else {// ���²���
						m = A.indexOf(B, n);
						if (m != -1) {
							textArea.setCaretPosition(m + findtxt.getText().length());
							textArea.select(m, m + findtxt.getText().length());
						} else {
							if (xh.isSelected()) {// ���ѭ��
								m = A.indexOf(B);// ��ͷ��ʼ��
								if (m != -1) {
									textArea.setCaretPosition(m + findtxt.getText().length());
									textArea.select(m, m + findtxt.getText().length());
								} else {
									JOptionPane.showMessageDialog(null, "�Ҳ���  " + findtxt.getText() + "��", "��ʾ",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "�Ҳ���  " + findtxt.getText() + "��", "��ʾ",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
			}
		});

		bx2.add(bx3);
		bx2.add(Box.createHorizontalStrut(10));
		bx2.add(bx4);
		bx2.add(Box.createHorizontalStrut(10));
		bx2.add(bx5);
		bx2.add(Box.createHorizontalStrut(400));

		bx.add(Box.createVerticalStrut(20));
		bx.add(bx1);
		bx.add(bx2);
		bx.add(Box.createVerticalStrut(20));
		jd.getContentPane().add(bx);
		jd.setSize(562, 209);// ���ڴ�С
		jd.setLocationRelativeTo(null);// ����
		jd.setResizable(false);// ���ܸı��С
		jd.setVisible(true);// ��ʾ
	}

	public void findnextv() {
		// ������һ��
		JDialog jd = new JDialog(this, "������һ��", true);
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();
		JLabel findnr = new JLabel("��������(N)��");
		findnr.setFont(new Font("����", Font.PLAIN, 20));
		JTextField findtxt = new JTextField();
		findtxt.setFont(new Font("����", Font.PLAIN, 20));
		findtxt.setPreferredSize(new Dimension(210, 40));
		findtxt.setMaximumSize(new Dimension(210, 40));
		findtxt.setMinimumSize(new Dimension(210, 40));
		JButton jbfinext = new JButton("������һ��(F)");
		jbfinext.setFont(new Font("����", Font.PLAIN, 20));
		bx1.add(findnr);
		bx1.add(findtxt);
		bx1.add(Box.createHorizontalStrut(30));
		bx1.add(jbfinext);

		Box bx2 = Box.createHorizontalBox();
		Box bx3 = Box.createVerticalBox();
		JCheckBox qf = new JCheckBox("���ִ�Сд(C)", true);
		qf.setFont(new Font("����", Font.PLAIN, 16));
		JCheckBox xh = new JCheckBox("ѭ��(R)", true);
		xh.setFont(new Font("����", Font.PLAIN, 16));
		bx3.add(Box.createHorizontalStrut(20));
		bx3.add(qf);
		bx3.add(xh);

		Box bx4 = Box.createVerticalBox();
		JPanel jp = new JPanel();
		JRadioButton jr1 = new JRadioButton("����(U)");
		jr1.setFont(new Font("����", Font.PLAIN, 16));
		JRadioButton jr2 = new JRadioButton("����(D)", true);
		jr2.setFont(new Font("����", Font.PLAIN, 16));
		jp.add(jr1);
		jp.add(jr2);
		ButtonGroup gr = new ButtonGroup();
		gr.add(jr1);
		gr.add(jr2);
		jp.add(jr1);
//		jp.add(Box.createVerticalStrut(20));
		jp.add(jr2);
//		jp.add(Box.createVerticalStrut(20));
		jp.setPreferredSize(new Dimension(205, 60));
		jp.setMaximumSize(new Dimension(205, 60));
		jp.setMinimumSize(new Dimension(205, 60));

		jp.setBorder(BorderFactory.createTitledBorder("����"));
		bx4.add(jp);
//		bx4.add(Box.createVerticalStrut(30));

		Box bx5 = Box.createVerticalBox();
		JButton cancel = new JButton("ȡ��");
		cancel.setFont(new Font("����", Font.PLAIN, 20));
		cancel.setPreferredSize(new Dimension(152, 43));
		cancel.setMaximumSize(new Dimension(152, 43));
		cancel.setMinimumSize(new Dimension(152, 43));
//		bx5.add(Box.createHorizontalStrut(200));
		bx5.add(Box.createVerticalStrut(10));
		bx5.add(cancel);
		bx5.add(Box.createHorizontalStrut(26));

		// ȡ��
		cancel.addActionListener(new ActionListener() {
			// ȡ��
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jd.dispose();// �رմ���
			}
		});

		// ������һ��
		jbfinext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String areastr = textArea.getText();// ��ȡ�ı����ı�
				String fieldstr = findtxt.getText();// ��ȡ�ı����ı�
				String toupparea = areastr.toUpperCase();// תΪ��д���������ִ�Сд�жϷ������
				String touppfield = fieldstr.toUpperCase();
				String A;// �������ҵ��ı�������
				String B;// �������ҵ��ı�������
				if (qf.isSelected()) {// ���ִ�Сд
					A = areastr;
					B = fieldstr;
				} else {// ȫ����Ϊ��д
					A = toupparea;
					B = touppfield;
				}
				if (findtxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "��������ҵ����ݣ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
				} else {
					int n = textArea.getCaretPosition();// ��ȡ����λ��
					int m = 0;
					if (jr1.isSelected()) {// ���ϲ���
						if (textArea.getSelectedText() == null) {
							m = A.lastIndexOf(B, n - 1);
						} else {
							m = A.lastIndexOf(B, n - findtxt.getText().length() - 1);
						}
						if (m != -1) {
							textArea.setCaretPosition(m);
							textArea.select(m, m + findtxt.getText().length());
						} else {
							if (xh.isSelected()) {// ���ѭ��
								m = A.lastIndexOf(B);// �Ӻ��濪ʼ��
								if (m != -1) {
									textArea.setCaretPosition(m);
									textArea.select(m, m + findtxt.getText().length());
								} else {
									JOptionPane.showMessageDialog(null, "�Ҳ���  " + findtxt.getText() + "��", "��ʾ",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "�Ҳ���  " + findtxt.getText() + "��", "��ʾ",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					} else {// ���²���
						m = A.indexOf(B, n);
						if (m != -1) {
							textArea.setCaretPosition(m + findtxt.getText().length());
							textArea.select(m, m + findtxt.getText().length());
						} else {
							if (xh.isSelected()) {// ���ѭ��
								m = A.indexOf(B);// ��ͷ��ʼ��
								if (m != -1) {
									textArea.setCaretPosition(m + findtxt.getText().length());
									textArea.select(m, m + findtxt.getText().length());
								} else {
									JOptionPane.showMessageDialog(null, "�Ҳ���  " + findtxt.getText() + "��", "��ʾ",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "�Ҳ���  " + findtxt.getText() + "��", "��ʾ",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
			}
		});

		bx2.add(bx3);
		bx2.add(Box.createHorizontalStrut(10));
		bx2.add(bx4);
		bx2.add(Box.createHorizontalStrut(10));
		bx2.add(bx5);
		bx2.add(Box.createHorizontalStrut(400));

		bx.add(Box.createVerticalStrut(20));
		bx.add(bx1);
		bx.add(bx2);
		bx.add(Box.createVerticalStrut(20));
		jd.getContentPane().add(bx);
		jd.setSize(562, 209);// ���ڴ�С
		jd.setLocationRelativeTo(null);// ����
		jd.setResizable(false);// ���ܸı��С
		jd.setVisible(true);// ��ʾ
	}

	public void replacev() {
		// �滻
		JDialog jd = new JDialog(this, "�滻", true);
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();// ˮƽ
		Box bx3 = Box.createVerticalBox();

		Box bx4 = Box.createHorizontalBox();
		JLabel jb1 = new JLabel("��������(N):");
		jb1.setFont(new Font("����", Font.PLAIN, 20));
		JTextField jt1 = new JTextField();
		jt1.setText("1");
		jt1.selectAll();
		jt1.setPreferredSize(new Dimension(210, 40));
		jt1.setMaximumSize(new Dimension(210, 40));
		jt1.setMinimumSize(new Dimension(210, 40));
		jt1.setFont(new Font("����", Font.PLAIN, 20));
		bx4.add(jb1);
		bx4.add(jt1);
		Box bx5 = Box.createHorizontalBox();
		JLabel jb2 = new JLabel("�滻Ϊ(P):  ");
		jb2.setFont(new Font("����", Font.PLAIN, 20));
		JTextField jt2 = new JTextField();
		jt2.setPreferredSize(new Dimension(210, 40));
		jt2.setMaximumSize(new Dimension(210, 40));
		jt2.setMinimumSize(new Dimension(210, 40));
		jt2.setFont(new Font("����", Font.PLAIN, 20));
		bx5.add(jb2);
		bx5.add(jt2);

		Box bx7 = Box.createHorizontalBox();
		Box bx8 = Box.createVerticalBox();
		JCheckBox qf = new JCheckBox("���ִ�Сд(C)", true);
		qf.setFont(new Font("����", Font.PLAIN, 16));
		JCheckBox xh = new JCheckBox("ѭ��(R)", true);
		xh.setFont(new Font("����", Font.PLAIN, 16));

		bx8.add(qf);
		bx8.add(xh);
		bx7.add(Box.createHorizontalStrut(10));
		bx7.add(bx8);
		bx7.add(Box.createVerticalStrut(200));

		Box bx6 = Box.createVerticalBox();
		JButton jbu1 = new JButton("������һ��(F)");
		jbu1.setFont(new Font("����", Font.PLAIN, 20));
		JButton jbu2 = new JButton("�滻(R)");
		jbu2.setPreferredSize(new Dimension(152, 43));
		jbu2.setMaximumSize(new Dimension(152, 43));
		jbu2.setMinimumSize(new Dimension(152, 43));
		jbu2.setFont(new Font("����", Font.PLAIN, 20));
		JButton jbu3 = new JButton("ȫ���滻(A)");
		jbu3.setPreferredSize(new Dimension(152, 43));
		jbu3.setMaximumSize(new Dimension(152, 43));
		jbu3.setMinimumSize(new Dimension(152, 43));
		jbu3.setFont(new Font("����", Font.PLAIN, 20));
		JButton jbu4 = new JButton("ȡ��");
		jbu4.setPreferredSize(new Dimension(152, 43));
		jbu4.setMaximumSize(new Dimension(152, 43));
		jbu4.setMinimumSize(new Dimension(152, 43));
		jbu4.setFont(new Font("����", Font.PLAIN, 20));
		bx6.add(Box.createVerticalStrut(10));
		bx6.add(jbu1);
		bx6.add(Box.createVerticalStrut(10));
		bx6.add(jbu2);
		bx6.add(Box.createVerticalStrut(10));
		bx6.add(jbu3);
		bx6.add(Box.createVerticalStrut(10));
		bx6.add(jbu4);
		bx6.add(Box.createVerticalStrut(80));

		bx3.add(Box.createHorizontalStrut(15));
		bx3.add(Box.createVerticalStrut(15));
		bx3.add(bx4);
		bx3.add(Box.createVerticalStrut(15));
		bx3.add(bx5);
		bx3.add(bx7);

		bx1.add(bx3);
		bx1.add(Box.createHorizontalStrut(20));
		bx1.add(bx6);

		jbu4.addActionListener(new ActionListener() {
			// ȡ��
			@Override
			public void actionPerformed(ActionEvent e) {
				jd.dispose();
			}
		});
		// ������һ��
		jbu1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String areastr = textArea.getText();// ��ȡ�ı����ı�
				String fieldstr = jt1.getText();// ��ȡ�ı����ı�
				String toupparea = areastr.toUpperCase();// תΪ��д���������ִ�Сд�жϷ������
				String touppfield = fieldstr.toUpperCase();
				String A;// �������ҵ��ı�������
				String B;// �������ҵ��ı�������
				if (qf.isSelected()) {// ���ִ�Сд
					A = areastr;
					B = fieldstr;
				} else {// ȫ����Ϊ��д
					A = toupparea;
					B = touppfield;
				}
				if (jt1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "��������ҵ����ݣ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
					jt1.requestFocus(true);
				} else {
					int i = textArea.getCaretPosition();
					int m = A.indexOf(B, i);
					if (m != -1) {
						textArea.setCaretPosition(m + fieldstr.length());
						textArea.select(m, m + fieldstr.length());
					} else {
						if (xh.isSelected()) {
							m = A.indexOf(B);// ��ͷ��ʼ��
							if (m != -1) {
								textArea.setCaretPosition(m + fieldstr.length());
								textArea.select(m, m + fieldstr.length());
							} else {
								JOptionPane.showMessageDialog(null, "�Ҳ���  " + jt1.getText() + "��", "��ʾ",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "�Ҳ���  " + jt1.getText() + "��", "��ʾ",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});

		// �滻
		jbu2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (textArea.getSelectedText() == null) {
					JOptionPane.showMessageDialog(null, "û��ѡ���滻������ ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					jt1.requestFocus(true);
				} else if (jt2.getText().length() == 0 && textArea.getSelectedText() != null) {
					textArea.replaceSelection("");
				} else if (jt2.getText().length() > 0 && textArea.getSelectedText() != null) {
					textArea.replaceSelection(jt2.getText());
				}
			}

		});

		// ȫ���滻
		jbu3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setCaretPosition(0); // �����ŵ��༭����ͷ
				int a = 0, b = 0, replaceCount = 0;

				if (jt1.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "��������ҵ����ݣ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
					jt1.requestFocus(true);
					return;
				}
				while (a > -1) {

					int FindStartPos = textArea.getCaretPosition();
					String str1, str2, str3, str4, strA, strB;
					str1 = textArea.getText();
					str2 = str1.toLowerCase();
					str3 = jt1.getText();
					str4 = str3.toLowerCase();

					if (qf.isSelected()) {
						strA = str1;
						strB = str3;
					} else {
						strA = str2;
						strB = str4;
					}

					if (xh.isSelected()) {
						if (textArea.getSelectedText() == null) {
							a = strA.indexOf(strB, FindStartPos);
						} else {
							a = strA.indexOf(strB, FindStartPos - jt1.getText().length() + 1);
						}
					}
					if (a > -1) {
						if (xh.isSelected()) {
							textArea.setCaretPosition(a);
							b = jt1.getText().length();
							textArea.select(a, a + b);
						}
					} else {
						if (replaceCount == 0) {
							JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ�����!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "�ɹ��滻" + replaceCount + "��ƥ������!", "��ʾ",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
					if (jt2.getText().length() == 0 && textArea.getSelectedText() != null) {
						textArea.replaceSelection("");
						replaceCount++;
					}
					if (jt2.getText().length() > 0 && textArea.getSelectedText() != null) {
						textArea.replaceSelection(jt2.getText());
						replaceCount++;
					}
				}
			}

		});

		bx.add(bx1);
		jd.getContentPane().add(bx);
//		jf.setAlwaysOnTop(true);
		jd.setSize(537, 288);// ���ڴ�С
		jd.setLocationRelativeTo(null);// ����
		jd.setResizable(false);// ���ܸı��С
		jd.setVisible(true);// ��ʾ
	}
}
