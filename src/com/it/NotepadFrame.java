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
	// 新建文件、打开文件、保存文件、另存为、页面设置、打印、退出
	private JMenuItem newFile, openFile, saverFile, thesaver, pageset, printFile, exit;
	// 撤销、剪切、复制、粘贴、删除、搜索、查找、查找下一个、替换、转到、全选、时间/日期
	private JMenuItem repeal, shear, copy, paste, delete, search, find, findnext, replace, togo, all, date;
	// 行、字体、字体颜色、背景颜色
	private JMenuItem font, fontcolor, bgcolor;
	// 状态栏、自动换行
	private JCheckBoxMenuItem statusbar, line;
	// 发送反馈、关于、帮助
	private JMenuItem send, about, help;
	// 内容区域
	public JTextArea textArea;
	// 滚动条
	private JScrollPane scrollPane;
	// 转到togo
	JDialog jd;
	// 撤销管理器
	public UndoManager mgr = new UndoManager();

	public static String name;// 当前计算机用户名

	PrintJob p = null;// 声明一个要打印的对象
	Graphics g = null;// 要打印的对象
	// 系统剪切板ss
	Toolkit t = Toolkit.getDefaultToolkit();
	Clipboard clip = t.getSystemClipboard();
//	repeal, shear, copy, paste, delete, search, find, findnext, replace, togo, all, date

	
	//缩小
	JMenuItem shrink;
	//放大
	JMenuItem amplify;
	//恢复
	JMenuItem regain;

	String oldValue;// 存放编辑区原来的内容，用于比较文本是否有改动
	boolean isNewFile = true;// 是否新文件(未保存过的)
	File currentFile;// 当前文件名
	
	int textAreasize;//字体大小
	String textAreaname;//字体名称
	int textAreastyle;//字体样式

	// 弹窗消息 是、否、取消 0--是、1--否、2--取消
	private JPopupMenu jpm; // 右键弹出菜单
	private JMenuItem repeal2; // 撤销
	private JMenuItem shear2; // 剪切
	private JMenuItem copy2; // 复制
	private JMenuItem paste2; // 粘贴
	private JMenuItem delete2; // 删除
	private JMenuItem all2; // 全选
	private JMenuItem toLeft; // 从右到左的阅读顺序
	private JMenuItem showUnicode; // 显示Unicode控制字符
	private JMenuItem close; // 关闭IME
	private JMenuItem unicode; // 插入Unicode控制字符
	private JMenuItem reselect; // 汉字重选
	private JMenuItem search2; // 搜索
	public int linenum = 1;// 行
	int columnnum = 1;// 列
	JToolBar tool;// 底部状态栏
	JLabel jb1, jb2;
	int length = 0;// 长度

	public NotepadFrame() {
		this.setSize(1290, 835);
		this.setTitle("记事本");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon("images/notepad.png").getImage());
		JPanel p = CreatePanl();

		this.setContentPane(p);// 面板
		this.setJMenuBar(cdt());// 菜单栏
		this.setVisible(true);// 显示
	}

	private JPanel CreatePanl() {
		// TODO Auto-generated method stub
		JPanel jp = new JPanel();
//		jp.setBorder(new EmptyBorder(0, 0, 0, 0));
//		// 设置边框布局
		jp.setLayout(new BorderLayout(0, 0));
		textArea = new JTextArea(73, 200);// 设置行列
		textArea.setBorder(null);// 设置点击时不会出现蓝边
		textArea.setWrapStyleWord(true); // 文字长度超过一行时自动换行
		textArea.setLineWrap(true); // 文本编辑区默认自动换行
		textArea.setFont(new Font("微软雅黑", Font.PLAIN, 21));// 字体大小
		textAreasize=textArea.getFont().getSize();
		textAreaname=textArea.getFont().getFamily();
		textAreastyle=textArea.getFont().getStyle();
		
		oldValue = textArea.getText();// 获取原文本编辑区的内容
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);// 滚动条
		jpm = new JPopupMenu();// 右键的菜单栏
		addPopup(textArea, jpm);
//		repeal, shear, copy, paste, delete, search, find, findnext, replace, togo, all, date
		repeal2 = new JMenuItem("撤销(U)");
		repeal2.addActionListener(this);
		jpm.add(repeal2);
		jpm.addSeparator();
		shear2 = new JMenuItem("剪切(T)");
		shear2.addActionListener(this);
		jpm.add(shear2);
		copy2 = new JMenuItem("复制(C)");
		copy2.addActionListener(this);
		jpm.add(copy2);
		paste2 = new JMenuItem("粘贴(P)");
		paste2.addActionListener(this);
		jpm.add(paste2);
		delete2 = new JMenuItem("删除(D)");
		delete2.addActionListener(this);
		jpm.add(delete2);
		jpm.addSeparator();
		all2 = new JMenuItem("全选(A)");
		all2.addActionListener(this);
		jpm.add(all2);
		jpm.addSeparator();
		toLeft = new JMenuItem("从右到左的阅读顺序(R)");
		toLeft.addActionListener(this);
		jpm.add(toLeft);
		showUnicode = new JMenuItem("显示Unicode控制字符(S)");
		showUnicode.setVisible(true);
		Color c = new Color(253, 253, 253);
		showUnicode.addActionListener(this);
		jpm.add(showUnicode);
		unicode = new JMenuItem("插入Unicode控制字符(I)");
		
		unicode.addActionListener(this);
		jpm.add(unicode);
		jpm.addSeparator();
		close = new JMenuItem("关闭输入法(L)");
		close.addActionListener(this);
		jpm.add(close);
		reselect = new JMenuItem("汉字重选(R)");
		reselect.addActionListener(this);
		jpm.add(reselect);
		jpm.addSeparator();
		search2 = new JMenuItem("使用sogou搜索");
		search2.addActionListener(this);
		jpm.add(search2);
		jp.add(scrollPane, BorderLayout.CENTER);
		// 设置状态栏
		tool = new JToolBar();
		tool.setSize(textArea.getSize().width, 10);// toolState.setLayout(new FlowLayout(FlowLayout.LEFT));
		jb1 = new JLabel("    第 " + linenum + " 行, 第 " + columnnum + " 列  ");
		tool.add(jb1); // 添加行数列数
		tool.addSeparator();

		jb2 = new JLabel("    一共 " + length + " 字  ");
		tool.add(jb2); // 添加字数统计
		tool.setFloatable(false);
		textArea.addCaretListener(new CaretListener() { // 记录行数和列数
			public void caretUpdate(CaretEvent e) {
				// sum=0;
				JTextArea editArea = (JTextArea) e.getSource();
				try {
					int caretpos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretpos);
					columnnum = caretpos - textArea.getLineStartOffset(linenum);
					linenum += 1;
					jb1.setText("    第 " + linenum + " 行, 第 " + (columnnum + 1) + " 列  ");
					// sum+=columnnum+1;
					// length+=sum;
					length = NotepadFrame.this.textArea.getText().toString().length();
					jb2.setText("    一共 " + length + " 字  ");
				} catch (Exception ex) {

				}
			}
		});
		jp.add(tool, BorderLayout.SOUTH);// 面板添加工具栏
//	、	final JPopupMenu jg = new JPopupMenu(); // 创建弹出式菜单，下面三项是菜单项
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)// 只响应鼠标右键单击事件
				{
					// 菜单
					jpm.show(e.getComponent(), e.getX(), e.getY());// 在鼠标位置显示弹出式菜单
				}
			}
		});
		// 撤销的监听
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
		return jp;// 返回面板
	}

	// 显示右键 菜单
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
	// 创建菜单

	private JMenuBar cdt() {// 菜单栏
//		newFile,openFile,saverFile,thesaver,pageset,printFile,exit;
		JMenuBar jb = new JMenuBar();
		JMenu jm = new JMenu("文件(F)");
		jm.setFont(new Font("楷体", Font.BOLD, 20));
		newFile = new JMenuItem("新建(N) ");
		newFile.setFont(new Font("楷体", Font.BOLD, 20));
		// 设置快捷键
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		newFile.addActionListener(this);

		openFile = new JMenuItem("打开(O) ");
		openFile.setFont(new Font("楷体", Font.BOLD, 20));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		openFile.addActionListener(this);

		saverFile = new JMenuItem("保存(S) ");
		saverFile.setFont(new Font("楷体", Font.BOLD, 20));
		saverFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
		saverFile.addActionListener(this);

		thesaver = new JMenuItem("另存为(A) ");
		thesaver.setFont(new Font("楷体", Font.BOLD, 20));
		thesaver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_MASK));
		thesaver.addActionListener(this);

		pageset = new JMenuItem("页面设置(U) ");
		pageset.setFont(new Font("楷体", Font.BOLD, 20));
		pageset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
		pageset.addActionListener(this);

		printFile = new JMenuItem("打印(P) ");
		printFile.setFont(new Font("楷体", Font.BOLD, 20));
		printFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		printFile.addActionListener(this);
		exit = new JMenuItem("退出(X) ");
		exit.setFont(new Font("楷体", Font.BOLD, 20));
		exit.addActionListener(this);
		// 事件监听

		jm.add(newFile);
		jm.add(openFile);
		jm.add(saverFile);
		jm.add(thesaver);
		jm.addSeparator();// 线条
		jm.add(pageset);
		jm.add(printFile);
		jm.addSeparator();
		jm.add(exit);

		JMenu jm2 = new JMenu("编辑(E)");
		jm2.setFont(new Font("楷体", Font.BOLD, 20));
		repeal = new JMenuItem("撤销(U) ");
		repeal.setFont(new Font("楷体", Font.BOLD, 20));
		repeal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		repeal.addActionListener(this);

		shear = new JMenuItem("剪切(T) ");
		shear.setFont(new Font("楷体", Font.BOLD, 20));
		shear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		shear.addActionListener(this);

		copy = new JMenuItem("复制(C) ");
		copy.setFont(new Font("楷体", Font.BOLD, 20));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		copy.addActionListener(this);

		paste = new JMenuItem("粘贴(P) ");
		paste.setFont(new Font("楷体", Font.BOLD, 20));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		paste.addActionListener(this);

		delete = new JMenuItem("删除(L) ");
		delete.setFont(new Font("楷体", Font.BOLD, 20));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_MASK));
		delete.addActionListener(this);

		search = new JMenuItem("使用sogou搜索(T) ");
		search.setFont(new Font("楷体", Font.BOLD, 20));
		search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
		search.addActionListener(this);

		find = new JMenuItem("查找(F) ");
		find.setFont(new Font("楷体", Font.BOLD, 20));
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
		find.addActionListener(this);

		findnext = new JMenuItem("查找下一个(N) ");
		findnext.setFont(new Font("楷体", Font.BOLD, 20));
		findnext.setAccelerator(KeyStroke.getKeyStroke("F3"));
		findnext.addActionListener(this);

		replace = new JMenuItem("替换(R) ");
		replace.setFont(new Font("楷体", Font.BOLD, 20));
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
		replace.addActionListener(this);

		togo = new JMenuItem("转到(G) ");
		togo.setFont(new Font("楷体", Font.BOLD, 20));
		togo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_MASK));
		togo.addActionListener(this);

		all = new JMenuItem("全选(A) ");
		all.setFont(new Font("楷体", Font.BOLD, 20));
		all.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
		all.addActionListener(this);

		date = new JMenuItem("时间/日期(D) ");
		date.setFont(new Font("楷体", Font.BOLD, 20));
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
		JMenu jm3 = new JMenu("格式(O)");
		jm3.setFont(new Font("楷体", Font.BOLD, 20));
		line = new JCheckBoxMenuItem("自动换行(W)", true);
		line.setFont(new Font("楷体", Font.BOLD, 20));
		line.addActionListener(this);

		font = new JMenuItem("字体(F)");
		font.setFont(new Font("楷体", Font.BOLD, 20));
		font.addActionListener(this);

		fontcolor = new JMenuItem("字体颜色(C)");
		fontcolor.setFont(new Font("楷体", Font.BOLD, 20));
		fontcolor.addActionListener(this);

		bgcolor = new JMenuItem("背景颜色(B)");
		bgcolor.setFont(new Font("楷体", Font.BOLD, 20));
		bgcolor.addActionListener(this);
		jm3.add(line);
//		jm3.addSeparator();
		jm3.add(font);
//		jm3.addSeparator();
		jm3.add(fontcolor);
//		jm3.addSeparator();
		jm3.add(bgcolor);
//		statusbar
		JMenu jm4 = new JMenu("查看(V)");
		jm4.setFont(new Font("楷体", Font.BOLD, 20));
		JMenu zoom = new JMenu("缩放(Z)");
		zoom.setFont(new Font("楷体", Font.BOLD, 20));
		amplify=new JMenuItem ("放大");
		amplify.setFont(new Font("楷体", Font.BOLD, 20));
		amplify.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_MASK));
		shrink =new JMenuItem("缩小");
		shrink.setFont(new Font("楷体", Font.BOLD, 20));
		shrink.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_MASK));
		regain =new JMenuItem("恢复默认缩放");
		regain.setFont(new Font("楷体", Font.BOLD, 20));
		regain.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_MASK));
		statusbar = new JCheckBoxMenuItem("状态栏(S)", true);
		statusbar.setFont(new Font("楷体", Font.BOLD, 20));
		statusbar.addActionListener(this);
		amplify.addActionListener(this);
		shrink.addActionListener(this);
		regain.addActionListener(this);
		zoom.add(amplify);
		zoom.add(shrink);
		zoom.add(regain);
		jm4.add(zoom);
		jm4.add(statusbar);

		JMenu jm5 = new JMenu("帮助(H)");
		jm5.setFont(new Font("楷体", Font.BOLD, 20));
		help = new JMenuItem("查看帮助(H)");
		help.setFont(new Font("楷体", Font.BOLD, 20));
		help.addActionListener(this);

		send = new JMenuItem("发送反馈(F)");
		send.setFont(new Font("楷体", Font.BOLD, 20));
		send.addActionListener(this);

		about = new JMenuItem("关于记事本(A)");
		about.setFont(new Font("楷体", Font.BOLD, 20));
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

	// 事件
//	repeal, shear, copy, paste, delete, search, find, findnext, replace, togo, all, date
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == newFile) {
			// 新建
			newfile();
		} else if (e.getSource() == pageset) {
			// 页面设置
			PageFormat pf = new PageFormat();// 描述页面大小和方向
			PrinterJob.getPrinterJob().pageDialog(pf);// 控制打印
		} else if (e.getSource() == openFile) {
			// 打开
			open();
		} else if (e.getSource() == saverFile) {
			// 保存
			save();
		} else if (e.getSource() == thesaver) {
			// 另存为
			saveAs();
		} else if (e.getSource() == printFile) {
			// 打印文件
			print();
		} else if (e.getSource() == exit) {
			// 退出
			exit();
		} else if (e.getSource() == repeal || e.getSource() == repeal2) {
			// 撤销
			if (mgr.canUndo()) {
				mgr.undo();
			}
		} else if (e.getSource() == shear || e.getSource() == shear2) {
			// 剪切
			shearv();
		} else if (e.getSource() == copy || e.getSource() == copy2) {
			// 复制
			copyv();
		} else if (e.getSource() == paste || e.getSource() == paste2) {
			// 粘贴
			pastev();
		} else if (e.getSource() == delete || e.getSource() == delete2) {
			// 删除
			deletev();
		} else if (e.getSource() == search || e.getSource() == search2) {
			// 搜索
			searchv();
		} else if (e.getSource() == line) {
			// 自动换行
			if (line.getState()) {
				textArea.setLineWrap(true);// 显示
			} else {
				textArea.setLineWrap(false);// 隐藏
			}
		} else if (e.getSource() == statusbar) {
			// 底部状态栏显示隐藏状态
			if (statusbar.getState()) {
				tool.setVisible(true);// 显示
			} else {
				tool.setVisible(false);// 隐藏
			}
		} else if (e.getSource() == all || e.getSource() == all2) {
			// 全选
			allv();
		} else if (e.getSource() == date) {
			// 时间日期
			time();
		} else if (e.getSource() == togo) {
			// 转到
			togo();
		} else if (e.getSource() == help) {
			// 查看帮助 跳转到记事本服务
			try {
				java.awt.Desktop.getDesktop().browse(new java.net.URI(
						"https://cn.bing.com/search?q=%E8%8E%B7%E5%8F%96%E6%9C%89%E5%85%B3+windows+10+%E4%B8%AD%E7%9A%84%E8%AE%B0%E4%BA%8B%E6%9C%AC%E7%9A%84%E5%B8%AE%E5%8A%A9&filters=guid:%224466414-zh-hans-dia%22%20lang:%22zh-hans%22&form=T00032&ocid=HelpPane-BingIA"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == send) {
			// 发送反馈
			try {
				// 自动打开QQ
				java.awt.Desktop.getDesktop()
						.browse(new java.net.URI("http://wpa.qq.com/msgrd?v=3&uin=2730023435&site=qq&menu=yes"));
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == about) {
			// 关于
			name = System.getProperty("user.name");// 获取当前用户名
			Aboutnodepad ab = new Aboutnodepad();
		} else if (e.getSource() == font) {
			// 字体
			Fontv font = new Fontv(textArea.getFont());
			int returnValue = font.showFontDialog(this);
			if (returnValue == font.APPROVE_OPTION) {
				Font f = font.getSelectFont();
				textArea.setFont(f);// 设置字体
			} else {
				return;
			}
		} else if (e.getSource() == fontcolor) {
			// 字体颜色选择器
			Color color = JColorChooser.showDialog(this, "字体颜色选择", Color.BLACK);
			textArea.setForeground(color);// 设置字体颜色
		} else if (e.getSource() == bgcolor) {
			// 背景颜色选择
			Color color = JColorChooser.showDialog(this, "背景颜色选择", Color.BLACK);
			textArea.setBackground(color);// 设置背景颜色
		} else if (e.getSource() == reselect) {
			// 汉字重选
			rselect();
		} else if (e.getSource() == close) {
			// 关闭输入法
			Robot s1 = null;// 自动化测试类 控制鼠标
			try {
				s1 = new Robot();
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			s1.keyPress(KeyEvent.VK_SHIFT);// 按下shift键
			s1.keyRelease(KeyEvent.VK_SHIFT);// 释放shift键
		} else if (e.getSource() == toLeft) {
			// 向右对齐
		} else if (e.getSource() == showUnicode) {
			// 显示unicode字符
		} else if (e.getSource() == unicode) {
			// 插入unicode字符
		} else if (e.getSource() == find) {
			// 查找
			findv();
		} else if (e.getSource() == findnext) {
			// 查找下一个
			findnextv();
		} else if (e.getSource() == replace) {
			// 替换
			replacev();
		}else if(e.getSource()==amplify) {
			//放大
			int b=textArea.getFont().getSize();//得到字体的大小
			String fontname=textArea.getFont().getFamily();//得到字体的名称
			int h=textArea.getFont().getStyle();//得到字体样式
			textArea.setFont(new Font(fontname,h,b+20));//每次20
		}else if(e.getSource()==shrink) {
			//缩小
			int b=textArea.getFont().getSize();//得到字体的大小
			String fontname=textArea.getFont().getFamily();//得到字体名称
			int h=textArea.getFont().getStyle();//得到字体样式
			if(b==textAreasize) {//如果当前的等于之前得到的就设置为原来字体
				textArea.setFont(new Font(textAreaname,textAreastyle,textAreasize));
			}else{
				textArea.setFont(new Font(fontname,h,b-20));//每次减20
			}
		}else if(e.getSource()==regain) {
			//恢复默认缩放
			textArea.setFont(new Font(textAreaname,textAreastyle,textAreasize));
		}
	}

	public void newfile() {
		// 新建
		textArea.requestFocus();
		String currentValue = textArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue));
		if (isTextChange==false) {
			int saveChoose = JOptionPane.showConfirmDialog(this, "文件尚未保存，是否保存？", "提示",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (saveChoose == JOptionPane.YES_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("确定");
				fileChooser.setDialogTitle("另存为");
				int result = fileChooser.showSaveDialog(this);
				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				File saveFileName = fileChooser.getSelectedFile();
				if (saveFileName == null || saveFileName.getName().equals("")) {
					JOptionPane.showMessageDialog(this, "文件名不能为空！", "提示", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						FileWriter fw = new FileWriter(saveFileName);
						BufferedWriter bfw = new BufferedWriter(fw);
						bfw.write(textArea.getText(), 0, textArea.getText().length());
						bfw.flush();// 刷新该流的缓冲
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
				this.setTitle("记事本");
				isNewFile = true;
				mgr.discardAllEdits(); // 撤消所有的"撤消"操作
				oldValue = textArea.getText();
			} else if (saveChoose == JOptionPane.CANCEL_OPTION) {
				return;
			}
		} else {
			textArea.replaceRange("", 0, textArea.getText().length());
			this.setTitle("记事本");
			isNewFile = true;
			mgr.discardAllEdits();// 撤消所有的"撤消"操作
			oldValue = textArea.getText();
		}
	}

	public void open() {
		// 打开
		textArea.requestFocus();
		String currentValue = textArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue));
		if (isTextChange==false) {
			int saveChoose = JOptionPane.showConfirmDialog(this, "文件尚未保存，是否保存？", "提示",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (saveChoose == JOptionPane.YES_OPTION) {
				String str = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setApproveButtonText("确定");
				fileChooser.setDialogTitle("另存为");
				int result = fileChooser.showSaveDialog(this);
				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				File saveFileName = fileChooser.getSelectedFile();
				if (saveFileName == null || saveFileName.getName().equals("")) {
					JOptionPane.showMessageDialog(this, "文件名不能为空！", "提示", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						FileWriter fw = new FileWriter(saveFileName);
						BufferedWriter bfw = new BufferedWriter(fw);
						bfw.write(textArea.getText(), 0, textArea.getText().length());
						bfw.flush();// 刷新该流的缓冲
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
				// fileChooser.setApproveButtonText("确定");
				fileChooser.setDialogTitle("打开文件");
				int result = fileChooser.showOpenDialog(this);
				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				File fileName = fileChooser.getSelectedFile();
				if (fileName == null || fileName.getName().equals("")) {
					JOptionPane.showMessageDialog(this, "文件名不能为空！", "提示", JOptionPane.ERROR_MESSAGE);
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
			// fileChooser.setApproveButtonText("确定");
			fileChooser.setDialogTitle("打开文件");
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				return;
			}
			File fileName = fileChooser.getSelectedFile();
			if (fileName == null || fileName.getName().equals("")) {
				JOptionPane.showMessageDialog(this, "文件名不能为空！", "提示", JOptionPane.ERROR_MESSAGE);
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
		// 保存
		textArea.requestFocus();
		if (isNewFile) {
			String str = null;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// fileChooser.setApproveButtonText("确定");
			fileChooser.setDialogTitle("保存");
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				return;
			}
			File saveFileName = fileChooser.getSelectedFile();
			if (saveFileName == null || saveFileName.getName().equals("")) {
				JOptionPane.showMessageDialog(this, "文件名不能为空！", "提示", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					FileWriter fw = new FileWriter(saveFileName);
					BufferedWriter bfw = new BufferedWriter(fw);
					bfw.write(textArea.getText(), 0, textArea.getText().length());
					bfw.flush();// 刷新该流的缓冲
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
		// 另存为
		textArea.requestFocus();
		String str = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// fileChooser.setApproveButtonText("确定");
		fileChooser.setDialogTitle("另存为");
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}
		File saveFileName = fileChooser.getSelectedFile();
		if (saveFileName == null || saveFileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "文件名不能为空！", "提示", JOptionPane.ERROR_MESSAGE);
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
		// 退出
		int exitChoose = JOptionPane.showConfirmDialog(this, "确定要退出嘛？", "提示", JOptionPane.OK_CANCEL_OPTION);
		if (exitChoose == JOptionPane.OK_OPTION) {
			System.exit(0);
		} else {
			return;
		}
	}

//关闭窗口时调用
	public void exitWindowChoose() {
		textArea.requestFocus();
		String currentValue = textArea.getText();
		if (currentValue.equals(oldValue) == true) {
			System.exit(0);
		} else {
			int exitChoose = JOptionPane.showConfirmDialog(this, "文件未保存，是否保存？", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
			if (exitChoose == JOptionPane.YES_OPTION) { // boolean isSave=false;
				if (isNewFile) {
					String str = null;
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fileChooser.setApproveButtonText("确定");
					fileChooser.setDialogTitle("另存为");

					int result = fileChooser.showSaveDialog(this);

					if (result == JFileChooser.CANCEL_OPTION) {
						return;
					}

					File saveFileName = fileChooser.getSelectedFile();

					if (saveFileName == null || saveFileName.getName().equals("")) {
						JOptionPane.showMessageDialog(this, "文件名不能为空！", "提示", JOptionPane.ERROR_MESSAGE);
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

	public void print() {// 打印
		try {
			p = getToolkit().getPrintJob(this, "ok", null);// 创建一个Printfjob 对象 p
			g = p.getGraphics();// p 获取一个用于打印的 Graphics 的对象
			g.translate(1000, 800);
			textArea.printAll(g);
			p.end();// 释放对象 g
		} catch (Exception a) {

		}
	}

	public void copyv() {// 复制
		// 选择文本
		String nr = textArea.getSelectedText();
		// 字符串选择器
		StringSelection txt = new StringSelection(nr);
		clip.setContents(txt, null);
	}

	public void shearv() {// 剪切
		String nr = textArea.getSelectedText();
		StringSelection txt = new StringSelection(nr);
		clip.setContents(txt, null);
		int start = textArea.getSelectionStart();
		int end = textArea.getSelectionEnd();
		textArea.replaceRange("", start, end);
		textArea.requestFocus(true);
	}

	public void deletev() {
		// 删除
		int start = textArea.getSelectionStart();
		int end = textArea.getSelectionEnd();
		textArea.replaceRange("", start, end);
	}

	public void pastev() {
		// 粘贴
		Transferable contents = clip.getContents(this); // 获取粘贴板上的内容
		if (contents == null) {// 判断是不是为空
			return;
		}
		String text = "";// 保存修改的文本
		try {
			text = (String) contents.getTransferData(DataFlavor.stringFlavor); // 表示 Java Unicode String 类
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "粘贴内容必须为String类型 ！", "提示", JOptionPane.WARNING_MESSAGE);
		}
		int start = textArea.getSelectionStart();// 获取选中的起始位置
		int end = textArea.getSelectionEnd();// 获取选中的结束位置
		textArea.replaceRange(text, start, end);// 修改文本域的内容
	}

	public void searchv() {
		// sogou搜索
		String vg = textArea.getSelectedText();// 得到选中的内容
		String nr = textArea.getText();// 得到多行文本框的内容
		String cf = getStringNoBlank(nr);// 去掉文本的空格
		if (vg == null) {
			// 如果没有选中文本就把多行文本的内容来搜索
			if (!nr.equals("")) {// 判断是否有文本内容
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
			// 如果选中文本，则拿选中的文本来当搜索内容
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
		// 去掉文本的所有空格
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
		// 全选
		Color c = new Color(0, 120, 215);// #0078d7 red：0 g:120 blue:215
		textArea.setSelectionColor(c);
		textArea.setSelectedTextColor(Color.white);
		textArea.selectAll();
	}

	public void time() {
		// 时间
		int g = textArea.getSelectionEnd();
		if (g == 0) {// 没有选中内容
			// 时间日期
			Date t = new Date();// 当前时间
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");// 时间格式化
			String tiem = sd.format(t);
			textArea.insert(tiem, textArea.getCaretPosition());// 写入多行文本 （写入文本 得到当前光标的停留位置）
		} else {// 选中
			Date t = new Date();// 当前时间
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");// 时间格式化
			String tiem = sd.format(t);
			int start = textArea.getSelectionStart();
			int end = textArea.getSelectionEnd();
			textArea.replaceRange(tiem, start, end);
		}

	}
	public void togo() {
		// 转到
		final JDialog dg = new JDialog(this, "转到指定行", true);
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();
		JLabel jb1 = new JLabel("行号(L)：");
		jb1.setFont(new Font("楷体", Font.PLAIN, 20));
		bx1.add(jb1);
		bx1.add(Box.createHorizontalStrut(234));
		Box bx2 = Box.createHorizontalBox();
		JTextField jt1 = new JTextField();
		jt1.setFont(new Font("楷体", Font.PLAIN, 25));
		jt1.setPreferredSize(new Dimension(320, 40));
		jt1.setMaximumSize(new Dimension(320, 40));
		jt1.setMinimumSize(new Dimension(320, 40));
		jt1.setText("1");
		jt1.selectAll();// 全部选中
		bx2.add(Box.createHorizontalStrut(15));
		bx2.add(jt1);
		bx2.add(Box.createHorizontalStrut(10));
		Box bx3 = Box.createHorizontalBox();
		JButton jbu1 = new JButton("  转到  ");
		jbu1.setFont(new Font("楷体", Font.PLAIN, 20));
		JButton jbu2 = new JButton("  取消  ");
		jbu2.setFont(new Font("楷体", Font.PLAIN, 20));
		bx3.add(Box.createHorizontalStrut(110));
		bx3.add(jbu1);
		bx3.add(Box.createHorizontalStrut(10));
		bx3.add(jbu2);
		// 回车事件
		jt1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyTyped(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					jbu1.doClick();// 点击确认
				}
			}
		});

		jbu1.addActionListener(new ActionListener() {
			// 确定
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
					JOptionPane.showMessageDialog(null, "请输入行数 ！", "提示", JOptionPane.WARNING_MESSAGE);
					jt1.requestFocus(true);
					return;
				}

				if (gt < 2 || gt >= texth) {
					if (gt < 2) {
						textArea.setCaretPosition(0);// 光标的位置
					} else {
						JOptionPane.showMessageDialog(null, "超过了总行数 ！", "提示", JOptionPane.WARNING_MESSAGE);
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
			// 取消
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dg.dispose();// 关闭窗口
			}
		});

		bx.add(Box.createVerticalStrut(10));
		bx.add(bx1);
		bx.add(Box.createVerticalStrut(8));
		bx.add(bx2);
		bx.add(Box.createVerticalStrut(20));
		bx.add(bx3);
		bx.add(Box.createVerticalStrut(20));
		dg.getContentPane().add(bx);// 添加箱子
		dg.setSize(380, 190);// 窗口大小
		dg.setLocationRelativeTo(null);// 居中
		dg.setResizable(false);// 不能改变大小
		dg.setVisible(true);// 显示
	}

	public void rselect() {
		// 汉字重选
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
		// 查找
		JDialog jd = new JDialog(this, "查找", true);
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();
		JLabel findnr = new JLabel("查找内容(N)：");
		findnr.setFont(new Font("楷体", Font.PLAIN, 20));
		JTextField findtxt = new JTextField();
		findtxt.setFont(new Font("楷体", Font.PLAIN, 20));
		findtxt.setPreferredSize(new Dimension(210, 40));
		findtxt.setMaximumSize(new Dimension(210, 40));
		findtxt.setMinimumSize(new Dimension(210, 40));
		JButton jbfinext = new JButton("查找下一个(F)");
		jbfinext.setFont(new Font("楷体", Font.PLAIN, 20));
		bx1.add(findnr);
		bx1.add(findtxt);
		bx1.add(Box.createHorizontalStrut(30));
		bx1.add(jbfinext);

		Box bx2 = Box.createHorizontalBox();
		Box bx3 = Box.createVerticalBox();
		JCheckBox qf = new JCheckBox("区分大小写(C)", true);
		qf.setFont(new Font("楷体", Font.PLAIN, 16));
		JCheckBox xh = new JCheckBox("循环(R)", true);
		xh.setFont(new Font("楷体", Font.PLAIN, 16));
		bx3.add(Box.createHorizontalStrut(20));
		bx3.add(qf);
		bx3.add(xh);

		Box bx4 = Box.createVerticalBox();
		JPanel jp = new JPanel();
		JRadioButton jr1 = new JRadioButton("向上(U)");
		jr1.setFont(new Font("楷体", Font.PLAIN, 16));
		JRadioButton jr2 = new JRadioButton("向下(D)", true);
		jr2.setFont(new Font("楷体", Font.PLAIN, 16));
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

		jp.setBorder(BorderFactory.createTitledBorder("方向"));
		bx4.add(jp);
//		bx4.add(Box.createVerticalStrut(30));

		Box bx5 = Box.createVerticalBox();
		JButton cancel = new JButton("取消");
		cancel.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel.setPreferredSize(new Dimension(152, 43));
		cancel.setMaximumSize(new Dimension(152, 43));
		cancel.setMinimumSize(new Dimension(152, 43));
//		bx5.add(Box.createHorizontalStrut(200));
		bx5.add(Box.createVerticalStrut(10));
		bx5.add(cancel);
		bx5.add(Box.createHorizontalStrut(26));

		// 取消
		cancel.addActionListener(new ActionListener() {
			// 取消
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jd.dispose();// 关闭窗口
			}
		});

		// 查找下一个
		jbfinext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String areastr = textArea.getText();// 获取文本区文本
				String fieldstr = findtxt.getText();// 获取文本框文本
				String toupparea = areastr.toUpperCase();// 转为大写，用做区分大小写判断方便查找
				String touppfield = fieldstr.toUpperCase();
				String A;// 用做查找的文本域内容
				String B;// 用作查找的文本框内容
				if (qf.isSelected()) {// 区分大小写
					A = areastr;
					B = fieldstr;
				} else {// 全部换为大写
					A = toupparea;
					B = touppfield;
				}
				if (findtxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "请输入查找的内容！", "提示", JOptionPane.WARNING_MESSAGE);
				} else {
					int n = textArea.getCaretPosition();// 获取光标的位置
					int m = 0;
					if (jr1.isSelected()) {// 向上查找
						if (textArea.getSelectedText() == null) {
							m = A.lastIndexOf(B, n - 1);
						} else {
							m = A.lastIndexOf(B, n - findtxt.getText().length() - 1);
						}
						if (m != -1) {
							textArea.setCaretPosition(m);
							textArea.select(m, m + findtxt.getText().length());
						} else {
							if (xh.isSelected()) {// 如果循环
								m = A.lastIndexOf(B);// 从后面开始找
								if (m != -1) {
									textArea.setCaretPosition(m);
									textArea.select(m, m + findtxt.getText().length());
								} else {
									JOptionPane.showMessageDialog(null, "找不到  " + findtxt.getText() + "！", "提示",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "找不到  " + findtxt.getText() + "！", "提示",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					} else {// 向下查找
						m = A.indexOf(B, n);
						if (m != -1) {
							textArea.setCaretPosition(m + findtxt.getText().length());
							textArea.select(m, m + findtxt.getText().length());
						} else {
							if (xh.isSelected()) {// 如果循环
								m = A.indexOf(B);// 从头开始找
								if (m != -1) {
									textArea.setCaretPosition(m + findtxt.getText().length());
									textArea.select(m, m + findtxt.getText().length());
								} else {
									JOptionPane.showMessageDialog(null, "找不到  " + findtxt.getText() + "！", "提示",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "找不到  " + findtxt.getText() + "！", "提示",
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
		jd.setSize(562, 209);// 窗口大小
		jd.setLocationRelativeTo(null);// 居中
		jd.setResizable(false);// 不能改变大小
		jd.setVisible(true);// 显示
	}

	public void findnextv() {
		// 查找下一个
		JDialog jd = new JDialog(this, "查找下一个", true);
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();
		JLabel findnr = new JLabel("查找内容(N)：");
		findnr.setFont(new Font("楷体", Font.PLAIN, 20));
		JTextField findtxt = new JTextField();
		findtxt.setFont(new Font("楷体", Font.PLAIN, 20));
		findtxt.setPreferredSize(new Dimension(210, 40));
		findtxt.setMaximumSize(new Dimension(210, 40));
		findtxt.setMinimumSize(new Dimension(210, 40));
		JButton jbfinext = new JButton("查找下一个(F)");
		jbfinext.setFont(new Font("楷体", Font.PLAIN, 20));
		bx1.add(findnr);
		bx1.add(findtxt);
		bx1.add(Box.createHorizontalStrut(30));
		bx1.add(jbfinext);

		Box bx2 = Box.createHorizontalBox();
		Box bx3 = Box.createVerticalBox();
		JCheckBox qf = new JCheckBox("区分大小写(C)", true);
		qf.setFont(new Font("楷体", Font.PLAIN, 16));
		JCheckBox xh = new JCheckBox("循环(R)", true);
		xh.setFont(new Font("楷体", Font.PLAIN, 16));
		bx3.add(Box.createHorizontalStrut(20));
		bx3.add(qf);
		bx3.add(xh);

		Box bx4 = Box.createVerticalBox();
		JPanel jp = new JPanel();
		JRadioButton jr1 = new JRadioButton("向上(U)");
		jr1.setFont(new Font("楷体", Font.PLAIN, 16));
		JRadioButton jr2 = new JRadioButton("向下(D)", true);
		jr2.setFont(new Font("楷体", Font.PLAIN, 16));
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

		jp.setBorder(BorderFactory.createTitledBorder("方向"));
		bx4.add(jp);
//		bx4.add(Box.createVerticalStrut(30));

		Box bx5 = Box.createVerticalBox();
		JButton cancel = new JButton("取消");
		cancel.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel.setPreferredSize(new Dimension(152, 43));
		cancel.setMaximumSize(new Dimension(152, 43));
		cancel.setMinimumSize(new Dimension(152, 43));
//		bx5.add(Box.createHorizontalStrut(200));
		bx5.add(Box.createVerticalStrut(10));
		bx5.add(cancel);
		bx5.add(Box.createHorizontalStrut(26));

		// 取消
		cancel.addActionListener(new ActionListener() {
			// 取消
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jd.dispose();// 关闭窗口
			}
		});

		// 查找下一个
		jbfinext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String areastr = textArea.getText();// 获取文本区文本
				String fieldstr = findtxt.getText();// 获取文本框文本
				String toupparea = areastr.toUpperCase();// 转为大写，用做区分大小写判断方便查找
				String touppfield = fieldstr.toUpperCase();
				String A;// 用做查找的文本域内容
				String B;// 用作查找的文本框内容
				if (qf.isSelected()) {// 区分大小写
					A = areastr;
					B = fieldstr;
				} else {// 全部换为大写
					A = toupparea;
					B = touppfield;
				}
				if (findtxt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "请输入查找的内容！", "提示", JOptionPane.WARNING_MESSAGE);
				} else {
					int n = textArea.getCaretPosition();// 获取光标的位置
					int m = 0;
					if (jr1.isSelected()) {// 向上查找
						if (textArea.getSelectedText() == null) {
							m = A.lastIndexOf(B, n - 1);
						} else {
							m = A.lastIndexOf(B, n - findtxt.getText().length() - 1);
						}
						if (m != -1) {
							textArea.setCaretPosition(m);
							textArea.select(m, m + findtxt.getText().length());
						} else {
							if (xh.isSelected()) {// 如果循环
								m = A.lastIndexOf(B);// 从后面开始找
								if (m != -1) {
									textArea.setCaretPosition(m);
									textArea.select(m, m + findtxt.getText().length());
								} else {
									JOptionPane.showMessageDialog(null, "找不到  " + findtxt.getText() + "！", "提示",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "找不到  " + findtxt.getText() + "！", "提示",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					} else {// 向下查找
						m = A.indexOf(B, n);
						if (m != -1) {
							textArea.setCaretPosition(m + findtxt.getText().length());
							textArea.select(m, m + findtxt.getText().length());
						} else {
							if (xh.isSelected()) {// 如果循环
								m = A.indexOf(B);// 从头开始找
								if (m != -1) {
									textArea.setCaretPosition(m + findtxt.getText().length());
									textArea.select(m, m + findtxt.getText().length());
								} else {
									JOptionPane.showMessageDialog(null, "找不到  " + findtxt.getText() + "！", "提示",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "找不到  " + findtxt.getText() + "！", "提示",
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
		jd.setSize(562, 209);// 窗口大小
		jd.setLocationRelativeTo(null);// 居中
		jd.setResizable(false);// 不能改变大小
		jd.setVisible(true);// 显示
	}

	public void replacev() {
		// 替换
		JDialog jd = new JDialog(this, "替换", true);
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();// 水平
		Box bx3 = Box.createVerticalBox();

		Box bx4 = Box.createHorizontalBox();
		JLabel jb1 = new JLabel("查找内容(N):");
		jb1.setFont(new Font("楷体", Font.PLAIN, 20));
		JTextField jt1 = new JTextField();
		jt1.setText("1");
		jt1.selectAll();
		jt1.setPreferredSize(new Dimension(210, 40));
		jt1.setMaximumSize(new Dimension(210, 40));
		jt1.setMinimumSize(new Dimension(210, 40));
		jt1.setFont(new Font("楷体", Font.PLAIN, 20));
		bx4.add(jb1);
		bx4.add(jt1);
		Box bx5 = Box.createHorizontalBox();
		JLabel jb2 = new JLabel("替换为(P):  ");
		jb2.setFont(new Font("楷体", Font.PLAIN, 20));
		JTextField jt2 = new JTextField();
		jt2.setPreferredSize(new Dimension(210, 40));
		jt2.setMaximumSize(new Dimension(210, 40));
		jt2.setMinimumSize(new Dimension(210, 40));
		jt2.setFont(new Font("楷体", Font.PLAIN, 20));
		bx5.add(jb2);
		bx5.add(jt2);

		Box bx7 = Box.createHorizontalBox();
		Box bx8 = Box.createVerticalBox();
		JCheckBox qf = new JCheckBox("区分大小写(C)", true);
		qf.setFont(new Font("楷体", Font.PLAIN, 16));
		JCheckBox xh = new JCheckBox("循环(R)", true);
		xh.setFont(new Font("楷体", Font.PLAIN, 16));

		bx8.add(qf);
		bx8.add(xh);
		bx7.add(Box.createHorizontalStrut(10));
		bx7.add(bx8);
		bx7.add(Box.createVerticalStrut(200));

		Box bx6 = Box.createVerticalBox();
		JButton jbu1 = new JButton("查找下一个(F)");
		jbu1.setFont(new Font("楷体", Font.PLAIN, 20));
		JButton jbu2 = new JButton("替换(R)");
		jbu2.setPreferredSize(new Dimension(152, 43));
		jbu2.setMaximumSize(new Dimension(152, 43));
		jbu2.setMinimumSize(new Dimension(152, 43));
		jbu2.setFont(new Font("楷体", Font.PLAIN, 20));
		JButton jbu3 = new JButton("全部替换(A)");
		jbu3.setPreferredSize(new Dimension(152, 43));
		jbu3.setMaximumSize(new Dimension(152, 43));
		jbu3.setMinimumSize(new Dimension(152, 43));
		jbu3.setFont(new Font("楷体", Font.PLAIN, 20));
		JButton jbu4 = new JButton("取消");
		jbu4.setPreferredSize(new Dimension(152, 43));
		jbu4.setMaximumSize(new Dimension(152, 43));
		jbu4.setMinimumSize(new Dimension(152, 43));
		jbu4.setFont(new Font("楷体", Font.PLAIN, 20));
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
			// 取消
			@Override
			public void actionPerformed(ActionEvent e) {
				jd.dispose();
			}
		});
		// 查找下一个
		jbu1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String areastr = textArea.getText();// 获取文本区文本
				String fieldstr = jt1.getText();// 获取文本框文本
				String toupparea = areastr.toUpperCase();// 转为大写，用做区分大小写判断方便查找
				String touppfield = fieldstr.toUpperCase();
				String A;// 用做查找的文本域内容
				String B;// 用作查找的文本框内容
				if (qf.isSelected()) {// 区分大小写
					A = areastr;
					B = fieldstr;
				} else {// 全部换为大写
					A = toupparea;
					B = touppfield;
				}
				if (jt1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "请输入查找的内容！", "提示", JOptionPane.WARNING_MESSAGE);
					jt1.requestFocus(true);
				} else {
					int i = textArea.getCaretPosition();
					int m = A.indexOf(B, i);
					if (m != -1) {
						textArea.setCaretPosition(m + fieldstr.length());
						textArea.select(m, m + fieldstr.length());
					} else {
						if (xh.isSelected()) {
							m = A.indexOf(B);// 从头开始找
							if (m != -1) {
								textArea.setCaretPosition(m + fieldstr.length());
								textArea.select(m, m + fieldstr.length());
							} else {
								JOptionPane.showMessageDialog(null, "找不到  " + jt1.getText() + "！", "提示",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "找不到  " + jt1.getText() + "！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});

		// 替换
		jbu2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (textArea.getSelectedText() == null) {
					JOptionPane.showMessageDialog(null, "没有选中替换的内容 ", "提示", JOptionPane.INFORMATION_MESSAGE);
					jt1.requestFocus(true);
				} else if (jt2.getText().length() == 0 && textArea.getSelectedText() != null) {
					textArea.replaceSelection("");
				} else if (jt2.getText().length() > 0 && textArea.getSelectedText() != null) {
					textArea.replaceSelection(jt2.getText());
				}
			}

		});

		// 全部替换
		jbu3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setCaretPosition(0); // 将光标放到编辑区开头
				int a = 0, b = 0, replaceCount = 0;

				if (jt1.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "请输入查找的内容！", "提示", JOptionPane.WARNING_MESSAGE);
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
							JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "提示", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "成功替换" + replaceCount + "个匹配内容!", "提示",
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
		jd.setSize(537, 288);// 窗口大小
		jd.setLocationRelativeTo(null);// 居中
		jd.setResizable(false);// 不能改变大小
		jd.setVisible(true);// 显示
	}
}
