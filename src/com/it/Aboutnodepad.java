package com.it;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * ����
 * @author ����
 *
 */
public class Aboutnodepad extends JFrame implements ActionListener{
	public JDialog jd;
	public JButton jbu1;
	private JLabel jb3;
	
	JScrollPane js;
	JButton jbu2;
	public Aboutnodepad() {
		JFrame f = new JFrame();
		// ����һ��ģ̬����
		jd = new JDialog(f, "���ڼ��±�", true);
		jd.setSize(688, 620);
		jd.setTitle("����\"���±�\"");
		jd.setLocationRelativeTo(null);
		Color c=new Color(240,240,240);
		f.setBackground(c);
		JPanel p = CreatePanl();
		p.setBackground(c);
		jd.setResizable(false);
		jd.setContentPane(p);
		jd.setVisible(true);
	}

	private JPanel CreatePanl() {
		// TODO Auto-generated method stub
		JPanel jp=new JPanel();
		Box bx=Box.createVerticalBox();
		Box bx1=Box.createHorizontalBox();
		ImageIcon gh = new ImageIcon("images/abouthelp.png");
		JLabel jb1=new JLabel(gh);
		bx1.add(jb1);
		Box bx2=Box.createHorizontalBox();
		JLabel jb2=new JLabel("���� ");
		jb2.setFont(new Font("΢���ź�",Font.PLAIN,16));
		jb3=new JLabel("<html><u>���ʹ������<u/><html/>");
		jb3.setFont(new Font("΢���ź�",Font.PLAIN,16));
		Color c=new Color(0,127,222);
		jb3.setForeground(c);
		JLabel jb4=new JLabel("����������û�ʹ�ñ���Ʒ��");
		jb4.setFont(new Font("΢���ź�",Font.PLAIN,16));
		
		//��ǩ����¼�
		jb3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				new Serviceterms();//��ת����������
			}
		});
		
		
		bx2.add(Box.createHorizontalStrut(160));
		bx2.add(jb2);
		bx2.add(jb3);
		bx2.add(jb4);
		bx2.add(Box.createHorizontalStrut(350));
		
		Box bx3=Box.createHorizontalBox();
		String loname=NotepadFrame.name;
		JLabel jb5=new JLabel(loname);
		jb5.setFont(new Font("΢���ź�",Font.PLAIN,16));
		bx3.add(jb5);
		bx3.add(Box.createHorizontalStrut(450));
		
		Box bx4=Box.createHorizontalBox();
		JLabel jb6=new JLabel("��֯����");
		jb6.setFont(new Font("΢���ź�",Font.PLAIN,16));
		bx4.add(jb6);
		bx4.add(Box.createHorizontalStrut(420));
		Box bx5=Box.createHorizontalBox();
		jbu1=new JButton("     ȷ��     ");
		jbu1.setFont(new Font("΢���ź�",Font.PLAIN,16));
		jbu1.setBackground(Color.white);
		bx5.add(Box.createHorizontalStrut(533));
		bx5.add(jbu1);
		jbu1.addActionListener(this);
		
		bx.add(bx1);
		bx.add(bx2);
		bx.add(Box.createVerticalStrut(38));
		bx.add(bx3);
		bx.add(bx4);
		bx.add(Box.createVerticalStrut(30));
		bx.add(bx5);
		jp.add(bx);
		return jp;
	}
	//��ť����¼�
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jbu1) {
			//��ȷ���رհ�ť
			jd.dispose();
		}
	}

}
