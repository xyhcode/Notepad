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
 * 关于
 * @author 林沐
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
		// 创建一个模态窗口
		jd = new JDialog(f, "关于记事本", true);
		jd.setSize(688, 620);
		jd.setTitle("关于\"记事本\"");
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
		JLabel jb2=new JLabel("根据 ");
		jb2.setFont(new Font("微软雅黑",Font.PLAIN,16));
		jb3=new JLabel("<html><u>软件使用条款<u/><html/>");
		jb3.setFont(new Font("微软雅黑",Font.PLAIN,16));
		Color c=new Color(0,127,222);
		jb3.setForeground(c);
		JLabel jb4=new JLabel("，许可如下用户使用本产品：");
		jb4.setFont(new Font("微软雅黑",Font.PLAIN,16));
		
		//标签点击事件
		jb3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				new Serviceterms();//跳转到服务条款
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
		jb5.setFont(new Font("微软雅黑",Font.PLAIN,16));
		bx3.add(jb5);
		bx3.add(Box.createHorizontalStrut(450));
		
		Box bx4=Box.createHorizontalBox();
		JLabel jb6=new JLabel("组织名称");
		jb6.setFont(new Font("微软雅黑",Font.PLAIN,16));
		bx4.add(jb6);
		bx4.add(Box.createHorizontalStrut(420));
		Box bx5=Box.createHorizontalBox();
		jbu1=new JButton("     确定     ");
		jbu1.setFont(new Font("微软雅黑",Font.PLAIN,16));
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
	//按钮点击事件
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jbu1) {
			//点确定关闭按钮
			jd.dispose();
		}
	}

}
