package com.it;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class WindowsFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// ����Ƥ��
			// ���ñ����Խ��ı䴰�ڱ߿���ʽ����
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			// TODO exception
		}
		//windwosƤ����
//		try {
//			UIManager.setLookAndFeel(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getName());
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		NotepadFrame fr=new NotepadFrame();
	}

}
