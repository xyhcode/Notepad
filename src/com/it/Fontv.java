package com.it;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;


import javafx.scene.layout.Border;

/***
 * 字体设置
 * @author 林沐
 *
 */
public class Fontv extends JDialog {
	private JDialog jd;
	private Font fontd;
	//弹窗0，是
	public static final int CANCEL_OPTION = 0;
	//弹窗1  否
	public static final int APPROVE_OPTION = 1;
	// 字体选择框
	private JList font = null;
	// 样式选择器
	private JList fontstyle = null;
	// 文字大小选择器
	private JList fontsize = null;
	// 所有字体
	private String[] fontArray = null;
	// 所有样式
	private String[] styleArray = { "常规", "粗体", "斜体", "粗斜体" };
	// 所有预设字体大小
	private String[] sizeArray = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36",
			"48","72", "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号", "八号" };
	// 上面数组中对应的字体大小
	private int[] sizeIntArray = { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48,72, 42, 36, 26, 24, 22, 18, 16,
			15, 14, 12, 10, 9, 8, 7, 6, 5 };
	JComboBox cb;// 下拉列表
	// 字体
	private JTextField jft;
	// 字体类型
	private JTextField jft3;
	// 字体大小
	private JTextField jft4;
	// 中文预览
	private JRadioButton cpvw;
	// 英文预览
	private JRadioButton epvw;
	// 数字预览
	private JRadioButton npvw;
	// 确定取消
	private JButton confirm, cancel;

	// 中文预览的字符串
	private static final String CHINA_STRING = "微软雅黑";

	// 英文预览的字符串
	private static final String ENGLISH_STRING = "Come Baby";

	JLabel gdzt;// 更多字体
	JLabel pvmtxt;// 预览
	//返回
	private int returnValue = CANCEL_OPTION;

	public Fontv() {
		this(new Font("宋体", Font.PLAIN, 12));
	}

	public Fontv(Font f) {
		setTitle("字体");
		this.fontd = f;
		init();
		// 添加监听器
		addListener();
		// 按照预设字体显示
		setup();
		// 基本设置
		setModal(true);
		setResizable(false);
		// 自适应大小
		setSize(550, 625);
	}

	private void init() {
		//窗口内容
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();
		JLabel jb1 = new JLabel("字体(F):");
		jb1.setFont(new Font("楷体", Font.PLAIN, 20));
		JLabel jb2 = new JLabel("字形(Y):");
		jb2.setFont(new Font("楷体", Font.PLAIN, 20));
		JLabel jb3 = new JLabel("大小(S):");
		jb3.setFont(new Font("楷体", Font.PLAIN, 20));
		bx1.add(Box.createHorizontalStrut(15));
		bx1.add(jb1);
		bx1.add(Box.createHorizontalStrut(163));
		bx1.add(jb2);
		bx1.add(Box.createHorizontalStrut(100));
		bx1.add(jb3);
		bx1.add(Box.createHorizontalStrut(95));
		Box bx11 = Box.createHorizontalBox();
		Box bx2 = Box.createVerticalBox();
		jft = new JTextField();//字体
		jft.setFont(new Font("宋体",Font.PLAIN,20));
		jft.setPreferredSize(new Dimension(220, 35));
		jft.setMaximumSize(new Dimension(220, 35));
		jft.setMinimumSize(new Dimension(220, 35));
		jft.setEditable(false);
		GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();//得到系统字体
		fontArray = eq.getAvailableFontFamilyNames();
		font = new JList(fontArray);//字体
		font.setFont(new Font("宋体", Font.PLAIN, 20));
		JScrollPane sp1 = new JScrollPane(font);
		sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//去掉下面滚动条
		sp1.setPreferredSize(new Dimension(220, 145));
		sp1.setMaximumSize(new Dimension(220, 145));
		bx2.add(jft);
		bx2.add(sp1);

		Box bx3 = Box.createVerticalBox();
		jft3 = new JTextField();
		jft3.setFont(new Font("宋体",Font.PLAIN,20));
		jft3.setPreferredSize(new Dimension(167, 35));
		jft3.setMaximumSize(new Dimension(167, 35));
		jft3.setMinimumSize(new Dimension(167, 35));
		jft3.setEditable(false);
		fontstyle = new JList(styleArray);
		fontstyle.setFont(new Font("宋体", Font.PLAIN, 20));
		bx3.add(jft3);
		JScrollPane sp2 = new JScrollPane(fontstyle);
		sp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//去掉下面的滚动条
		sp2.setPreferredSize(new Dimension(167, 145));
		sp2.setMaximumSize(new Dimension(167, 145));
		bx3.add(sp2);

		Box bx4 = Box.createVerticalBox();
		jft4 = new JTextField("12");
		jft4.setFont(new Font("宋体",Font.PLAIN,20));
		jft4.setPreferredSize(new Dimension(82, 35));
		jft4.setMaximumSize(new Dimension(82, 35));
		jft4.setMinimumSize(new Dimension(82, 35));
		// 给文字大小文本框使用的Document文档，制定了一些输入字符的规则
		Document doc = new PlainDocument() {
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null) {
					return;
				}
				if (getLength() >= 3) {
					return;
				}
				if (!str.matches("[0-9]+") && !str.equals("初号") && !str.equals("小初") && !str.equals("一号")
						&& !str.equals("小一") && !str.equals("二号") && !str.equals("小二") && !str.equals("三号")
						&& !str.equals("小三") && !str.equals("四号") && !str.equals("小四") && !str.equals("五号")
						&& !str.equals("小五") && !str.equals("六号") && !str.equals("小六") && !str.equals("七号")
						&& !str.equals("八号")) {
					return;
				}
				super.insertString(offs, str, a);
				fontsize.setSelectedValue(jft4.getText(), true);
			}
		};
		jft4.setDocument(doc);
		fontsize = new JList(sizeArray);
		fontsize.setFont(new Font("宋体", Font.PLAIN, 20));
		bx4.add(jft4);
		JScrollPane sp3 = new JScrollPane(fontsize);
		sp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//去掉下拉滚动条
		sp3.setPreferredSize(new Dimension(82, 145));
		sp3.setMaximumSize(new Dimension(82, 145));
		sp3.setMinimumSize(new Dimension(82, 145));
		bx4.add(sp3);

		bx11.add(bx2);
		bx11.add(Box.createHorizontalStrut(20));
		bx11.add(bx3);
		bx11.add(Box.createHorizontalStrut(20));
		bx11.add(bx4);

		Box bx5 = Box.createHorizontalBox();
		pvmtxt = new JLabel(CHINA_STRING);
		pvmtxt.setHorizontalAlignment(JLabel.CENTER);//居中
		JPanel jpn = new JPanel();
		jpn.add(Box.createVerticalStrut(45));
		jpn.add(pvmtxt);
		jpn.setBorder(BorderFactory.createTitledBorder("示例"));//设置标题上边框
		bx5.add(Box.createHorizontalStrut(241));
		bx5.add(jpn);
		jpn.setPreferredSize(new Dimension(270, 90));
		jpn.setMaximumSize(new Dimension(270, 90));
		jpn.setMinimumSize(new Dimension(270, 90));

		Box bx6 = Box.createHorizontalBox();
		JLabel jb = new JLabel("脚本(R):");
		jb.setFont(new Font("宋体", Font.PLAIN, 20));
		bx6.add(Box.createHorizontalStrut(55));
		bx6.add(jb);

		Box bx7 = Box.createHorizontalBox();
		String[] sf = { "中文", "西欧语言"};//下拉框的内容
		cb = new JComboBox(sf);//下拉框
		cb.setFont(new Font("宋体", Font.PLAIN, 20));
		cb.setPreferredSize(new Dimension(269, 30));
		cb.setMaximumSize(new Dimension(269, 30));
		cb.setMinimumSize(new Dimension(269, 30));
		bx7.add(Box.createHorizontalStrut(240));
		bx7.add(cb);
		cb.setSelectedIndex(0);//默认是选择中文
		
		//下拉框的监听事件
		cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				// 当下拉框的某个选项被选中时
				if (cb.getSelectedIndex() == 0) {
					pvmtxt.setText(CHINA_STRING);//设置中文
				} else if (cb.getSelectedIndex() == 1) {
					pvmtxt.setText(ENGLISH_STRING);//设置英文
				}
			}

		});
		
		Box bx8 = Box.createHorizontalBox();
		gdzt = new JLabel("<html><u>更多字体<u/><html/>");
		gdzt.setFont(new Font("宋体", Font.PLAIN, 20));
		Color c = new Color(0, 127, 222);//蓝色
		gdzt.setForeground(c);//设置颜色
		bx8.add(Box.createHorizontalStrut(25));
		bx8.add(gdzt);
		
		//标签点击事件
		gdzt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				//跳转浏览器的字体
				try {
					java.awt.Desktop.getDesktop()
							.browse(new java.net.URI("https://www.17font.com/fonts/diannaoshangziti"));
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// confirm,cancel  确定  取消
		Box bx9 = Box.createHorizontalBox();
		confirm = new JButton("  确定  ");
		confirm.setFont(new Font("宋体", Font.PLAIN, 20));
		cancel = new JButton("  取消  ");
		cancel.setFont(new Font("宋体", Font.PLAIN, 20));

		bx9.add(Box.createHorizontalStrut(295));
		bx9.add(confirm);
		bx9.add(Box.createHorizontalStrut(10));
		bx9.add(cancel);

		bx.add(Box.createVerticalStrut(20));
		bx.add(bx1);
		bx.add(Box.createVerticalStrut(-1));
		bx.add(bx11);
		bx.add(Box.createVerticalStrut(40));
		bx.add(bx5);
		bx.add(Box.createVerticalStrut(10));
		bx.add(bx6);
		bx.add(Box.createVerticalStrut(5));
		bx.add(bx7);
		bx.add(Box.createVerticalStrut(50));
		bx.add(bx8);
		bx.add(Box.createVerticalStrut(20));
		bx.add(bx9);
		bx.add(Box.createVerticalStrut(10));
		getContentPane().add(bx);//添加箱子
	}
	//字体设置内容
	private void setup() {
		String fontName = fontd.getFamily();//得到字体名称
		int fontStyle = fontd.getStyle();//得到字体样式
		int fontSize = fontd.getSize();//得到字体的大小
		/*
		 * 如果预设的文字大小在选择列表中，则通过选择该列表中的某项进行设值，否则直接将预设文字大小写入文本框
		 */
		boolean b = false;
		for (int i = 0; i < sizeArray.length; i++) {
			if (sizeArray[i].equals(String.valueOf(fontSize))) {
				b = true;
				break;
			}
		}
		if (b) {
			// 选择文字大小列表中的某项
			fontsize.setSelectedValue(String.valueOf(fontSize), true);
		} else {
			jft4.setText(String.valueOf(fontSize));
		}
		// 选择字体列表中的某项
		font.setSelectedValue(fontName, true);
		// 选择样式列表中的某项
		fontstyle.setSelectedIndex(fontStyle);
		// 预览默认显示中文字符
//        chinaButton.doClick();    
		// 显示预览
		setPreview();
	}
	
	//监听事件
	private void addListener() {
		jft4.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				setPreview();
			}

			public void focusGained(FocusEvent e) {
				jft4.selectAll();
			}
		});
		// 字体列表发生选择事件的监听器
		font.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					jft.setText(String.valueOf(font.getSelectedValue()));
					// 设置预览
					setPreview();
				}
			}
		});
		fontstyle.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					jft3.setText(String.valueOf(fontstyle.getSelectedValue()));
					// 设置预览
					setPreview();
				}
			}
		});
		fontsize.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (!jft4.isFocusOwner()) {
						jft4.setText(String.valueOf(fontsize.getSelectedValue()));
					}
					// 设置预览
					setPreview();
				}
			}
		});
		// 确定按钮的事件监听
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 组合字体
				fontd = groupFont();
				// 设置返回值
				returnValue = APPROVE_OPTION;
				// 关闭窗口
				disposeDialog();
				
			}
		});
		// 取消按钮事件监听
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disposeDialog();
			}
		});
	}
	
	//窗口显示
	public final int showFontDialog(JFrame owner) {
		setLocationRelativeTo(owner);
		setVisible(true);
		return returnValue;
	}
	//得到返回的字体
	public final Font getSelectFont() {
		return fontd;
	}
	
	//窗口关闭
	private void disposeDialog() {
		Fontv.this.removeAll();
		Fontv.this.dispose();
	}
	
	//提示错误
	private void showErrorDialog(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
	}
	
	//设置显示文本
	private void setPreview() {
		Font f = groupFont();
		pvmtxt.setFont(f);
	}
	
	//得到返回的字体
	private Font groupFont() {
		String fontName = jft.getText();
		int fontStyle = fontstyle.getSelectedIndex();
		String sizeStr = jft4.getText().trim();
		// 如果没有输入
		if (sizeStr.length() == 0) {
			showErrorDialog("字体（大小）必须是有效“数值！");
			return null;
		}
		int fontSize = 0;
		// 通过循环对比文字大小输入是否在现有列表内
		for (int i = 0; i < sizeArray.length; i++) {
			if (sizeStr.equals(sizeArray[i])) {
				fontSize = sizeIntArray[i];
				break;
			}
		}
		// 没有在列表内
		if (fontSize == 0) {
			try {
				fontSize = Integer.parseInt(sizeStr);
				if (fontSize < 1) {
					showErrorDialog("字体（大小）必须是有效数值！");
					return null;
				}
			} catch (NumberFormatException nfe) {
				showErrorDialog("字体（大小）必须是有效数值！");
				return null;
			}
		}
		return new Font(fontName, fontStyle, fontSize);//返回得字体内容 ，字体样式， 字体大小
	}
}
