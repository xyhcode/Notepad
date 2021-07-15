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
 * ��������
 * @author ����
 *
 */
public class Fontv extends JDialog {
	private JDialog jd;
	private Font fontd;
	//����0����
	public static final int CANCEL_OPTION = 0;
	//����1  ��
	public static final int APPROVE_OPTION = 1;
	// ����ѡ���
	private JList font = null;
	// ��ʽѡ����
	private JList fontstyle = null;
	// ���ִ�Сѡ����
	private JList fontsize = null;
	// ��������
	private String[] fontArray = null;
	// ������ʽ
	private String[] styleArray = { "����", "����", "б��", "��б��" };
	// ����Ԥ�������С
	private String[] sizeArray = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36",
			"48","72", "����", "С��", "һ��", "Сһ", "����", "С��", "����", "С��", "�ĺ�", "С��", "���", "С��", "����", "С��", "�ߺ�", "�˺�" };
	// ���������ж�Ӧ�������С
	private int[] sizeIntArray = { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48,72, 42, 36, 26, 24, 22, 18, 16,
			15, 14, 12, 10, 9, 8, 7, 6, 5 };
	JComboBox cb;// �����б�
	// ����
	private JTextField jft;
	// ��������
	private JTextField jft3;
	// �����С
	private JTextField jft4;
	// ����Ԥ��
	private JRadioButton cpvw;
	// Ӣ��Ԥ��
	private JRadioButton epvw;
	// ����Ԥ��
	private JRadioButton npvw;
	// ȷ��ȡ��
	private JButton confirm, cancel;

	// ����Ԥ�����ַ���
	private static final String CHINA_STRING = "΢���ź�";

	// Ӣ��Ԥ�����ַ���
	private static final String ENGLISH_STRING = "Come Baby";

	JLabel gdzt;// ��������
	JLabel pvmtxt;// Ԥ��
	//����
	private int returnValue = CANCEL_OPTION;

	public Fontv() {
		this(new Font("����", Font.PLAIN, 12));
	}

	public Fontv(Font f) {
		setTitle("����");
		this.fontd = f;
		init();
		// ��Ӽ�����
		addListener();
		// ����Ԥ��������ʾ
		setup();
		// ��������
		setModal(true);
		setResizable(false);
		// ����Ӧ��С
		setSize(550, 625);
	}

	private void init() {
		//��������
		Box bx = Box.createVerticalBox();
		Box bx1 = Box.createHorizontalBox();
		JLabel jb1 = new JLabel("����(F):");
		jb1.setFont(new Font("����", Font.PLAIN, 20));
		JLabel jb2 = new JLabel("����(Y):");
		jb2.setFont(new Font("����", Font.PLAIN, 20));
		JLabel jb3 = new JLabel("��С(S):");
		jb3.setFont(new Font("����", Font.PLAIN, 20));
		bx1.add(Box.createHorizontalStrut(15));
		bx1.add(jb1);
		bx1.add(Box.createHorizontalStrut(163));
		bx1.add(jb2);
		bx1.add(Box.createHorizontalStrut(100));
		bx1.add(jb3);
		bx1.add(Box.createHorizontalStrut(95));
		Box bx11 = Box.createHorizontalBox();
		Box bx2 = Box.createVerticalBox();
		jft = new JTextField();//����
		jft.setFont(new Font("����",Font.PLAIN,20));
		jft.setPreferredSize(new Dimension(220, 35));
		jft.setMaximumSize(new Dimension(220, 35));
		jft.setMinimumSize(new Dimension(220, 35));
		jft.setEditable(false);
		GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();//�õ�ϵͳ����
		fontArray = eq.getAvailableFontFamilyNames();
		font = new JList(fontArray);//����
		font.setFont(new Font("����", Font.PLAIN, 20));
		JScrollPane sp1 = new JScrollPane(font);
		sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//ȥ�����������
		sp1.setPreferredSize(new Dimension(220, 145));
		sp1.setMaximumSize(new Dimension(220, 145));
		bx2.add(jft);
		bx2.add(sp1);

		Box bx3 = Box.createVerticalBox();
		jft3 = new JTextField();
		jft3.setFont(new Font("����",Font.PLAIN,20));
		jft3.setPreferredSize(new Dimension(167, 35));
		jft3.setMaximumSize(new Dimension(167, 35));
		jft3.setMinimumSize(new Dimension(167, 35));
		jft3.setEditable(false);
		fontstyle = new JList(styleArray);
		fontstyle.setFont(new Font("����", Font.PLAIN, 20));
		bx3.add(jft3);
		JScrollPane sp2 = new JScrollPane(fontstyle);
		sp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//ȥ������Ĺ�����
		sp2.setPreferredSize(new Dimension(167, 145));
		sp2.setMaximumSize(new Dimension(167, 145));
		bx3.add(sp2);

		Box bx4 = Box.createVerticalBox();
		jft4 = new JTextField("12");
		jft4.setFont(new Font("����",Font.PLAIN,20));
		jft4.setPreferredSize(new Dimension(82, 35));
		jft4.setMaximumSize(new Dimension(82, 35));
		jft4.setMinimumSize(new Dimension(82, 35));
		// �����ִ�С�ı���ʹ�õ�Document�ĵ����ƶ���һЩ�����ַ��Ĺ���
		Document doc = new PlainDocument() {
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null) {
					return;
				}
				if (getLength() >= 3) {
					return;
				}
				if (!str.matches("[0-9]+") && !str.equals("����") && !str.equals("С��") && !str.equals("һ��")
						&& !str.equals("Сһ") && !str.equals("����") && !str.equals("С��") && !str.equals("����")
						&& !str.equals("С��") && !str.equals("�ĺ�") && !str.equals("С��") && !str.equals("���")
						&& !str.equals("С��") && !str.equals("����") && !str.equals("С��") && !str.equals("�ߺ�")
						&& !str.equals("�˺�")) {
					return;
				}
				super.insertString(offs, str, a);
				fontsize.setSelectedValue(jft4.getText(), true);
			}
		};
		jft4.setDocument(doc);
		fontsize = new JList(sizeArray);
		fontsize.setFont(new Font("����", Font.PLAIN, 20));
		bx4.add(jft4);
		JScrollPane sp3 = new JScrollPane(fontsize);
		sp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//ȥ������������
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
		pvmtxt.setHorizontalAlignment(JLabel.CENTER);//����
		JPanel jpn = new JPanel();
		jpn.add(Box.createVerticalStrut(45));
		jpn.add(pvmtxt);
		jpn.setBorder(BorderFactory.createTitledBorder("ʾ��"));//���ñ����ϱ߿�
		bx5.add(Box.createHorizontalStrut(241));
		bx5.add(jpn);
		jpn.setPreferredSize(new Dimension(270, 90));
		jpn.setMaximumSize(new Dimension(270, 90));
		jpn.setMinimumSize(new Dimension(270, 90));

		Box bx6 = Box.createHorizontalBox();
		JLabel jb = new JLabel("�ű�(R):");
		jb.setFont(new Font("����", Font.PLAIN, 20));
		bx6.add(Box.createHorizontalStrut(55));
		bx6.add(jb);

		Box bx7 = Box.createHorizontalBox();
		String[] sf = { "����", "��ŷ����"};//�����������
		cb = new JComboBox(sf);//������
		cb.setFont(new Font("����", Font.PLAIN, 20));
		cb.setPreferredSize(new Dimension(269, 30));
		cb.setMaximumSize(new Dimension(269, 30));
		cb.setMinimumSize(new Dimension(269, 30));
		bx7.add(Box.createHorizontalStrut(240));
		bx7.add(cb);
		cb.setSelectedIndex(0);//Ĭ����ѡ������
		
		//������ļ����¼�
		cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				// ���������ĳ��ѡ�ѡ��ʱ
				if (cb.getSelectedIndex() == 0) {
					pvmtxt.setText(CHINA_STRING);//��������
				} else if (cb.getSelectedIndex() == 1) {
					pvmtxt.setText(ENGLISH_STRING);//����Ӣ��
				}
			}

		});
		
		Box bx8 = Box.createHorizontalBox();
		gdzt = new JLabel("<html><u>��������<u/><html/>");
		gdzt.setFont(new Font("����", Font.PLAIN, 20));
		Color c = new Color(0, 127, 222);//��ɫ
		gdzt.setForeground(c);//������ɫ
		bx8.add(Box.createHorizontalStrut(25));
		bx8.add(gdzt);
		
		//��ǩ����¼�
		gdzt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				//��ת�����������
				try {
					java.awt.Desktop.getDesktop()
							.browse(new java.net.URI("https://www.17font.com/fonts/diannaoshangziti"));
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// confirm,cancel  ȷ��  ȡ��
		Box bx9 = Box.createHorizontalBox();
		confirm = new JButton("  ȷ��  ");
		confirm.setFont(new Font("����", Font.PLAIN, 20));
		cancel = new JButton("  ȡ��  ");
		cancel.setFont(new Font("����", Font.PLAIN, 20));

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
		getContentPane().add(bx);//�������
	}
	//������������
	private void setup() {
		String fontName = fontd.getFamily();//�õ���������
		int fontStyle = fontd.getStyle();//�õ�������ʽ
		int fontSize = fontd.getSize();//�õ�����Ĵ�С
		/*
		 * ���Ԥ������ִ�С��ѡ���б��У���ͨ��ѡ����б��е�ĳ�������ֵ������ֱ�ӽ�Ԥ�����ִ�Сд���ı���
		 */
		boolean b = false;
		for (int i = 0; i < sizeArray.length; i++) {
			if (sizeArray[i].equals(String.valueOf(fontSize))) {
				b = true;
				break;
			}
		}
		if (b) {
			// ѡ�����ִ�С�б��е�ĳ��
			fontsize.setSelectedValue(String.valueOf(fontSize), true);
		} else {
			jft4.setText(String.valueOf(fontSize));
		}
		// ѡ�������б��е�ĳ��
		font.setSelectedValue(fontName, true);
		// ѡ����ʽ�б��е�ĳ��
		fontstyle.setSelectedIndex(fontStyle);
		// Ԥ��Ĭ����ʾ�����ַ�
//        chinaButton.doClick();    
		// ��ʾԤ��
		setPreview();
	}
	
	//�����¼�
	private void addListener() {
		jft4.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				setPreview();
			}

			public void focusGained(FocusEvent e) {
				jft4.selectAll();
			}
		});
		// �����б���ѡ���¼��ļ�����
		font.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					jft.setText(String.valueOf(font.getSelectedValue()));
					// ����Ԥ��
					setPreview();
				}
			}
		});
		fontstyle.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					jft3.setText(String.valueOf(fontstyle.getSelectedValue()));
					// ����Ԥ��
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
					// ����Ԥ��
					setPreview();
				}
			}
		});
		// ȷ����ť���¼�����
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �������
				fontd = groupFont();
				// ���÷���ֵ
				returnValue = APPROVE_OPTION;
				// �رմ���
				disposeDialog();
				
			}
		});
		// ȡ����ť�¼�����
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disposeDialog();
			}
		});
	}
	
	//������ʾ
	public final int showFontDialog(JFrame owner) {
		setLocationRelativeTo(owner);
		setVisible(true);
		return returnValue;
	}
	//�õ����ص�����
	public final Font getSelectFont() {
		return fontd;
	}
	
	//���ڹر�
	private void disposeDialog() {
		Fontv.this.removeAll();
		Fontv.this.dispose();
	}
	
	//��ʾ����
	private void showErrorDialog(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "����", JOptionPane.ERROR_MESSAGE);
	}
	
	//������ʾ�ı�
	private void setPreview() {
		Font f = groupFont();
		pvmtxt.setFont(f);
	}
	
	//�õ����ص�����
	private Font groupFont() {
		String fontName = jft.getText();
		int fontStyle = fontstyle.getSelectedIndex();
		String sizeStr = jft4.getText().trim();
		// ���û������
		if (sizeStr.length() == 0) {
			showErrorDialog("���壨��С����������Ч����ֵ��");
			return null;
		}
		int fontSize = 0;
		// ͨ��ѭ���Ա����ִ�С�����Ƿ��������б���
		for (int i = 0; i < sizeArray.length; i++) {
			if (sizeStr.equals(sizeArray[i])) {
				fontSize = sizeIntArray[i];
				break;
			}
		}
		// û�����б���
		if (fontSize == 0) {
			try {
				fontSize = Integer.parseInt(sizeStr);
				if (fontSize < 1) {
					showErrorDialog("���壨��С����������Ч��ֵ��");
					return null;
				}
			} catch (NumberFormatException nfe) {
				showErrorDialog("���壨��С����������Ч��ֵ��");
				return null;
			}
		}
		return new Font(fontName, fontStyle, fontSize);//���ص��������� ��������ʽ�� �����С
	}
}
