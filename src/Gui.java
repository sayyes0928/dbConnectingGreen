import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gui extends JFrame {

//	private String ����ȭ��_��ǰ��_�ܰ�_������_��ȸȭ��_��Ϲ�ư_��ȸ��ư_������ư��_��_ȭ�鱸��;
//	private String ��ǰ��;
//	private int �ܰ�;
//	private String ������;
//	private String JTable;
//	void ��Ϲ�ư_�̺�Ʈ_��Ϲ�ư��_������_������_�ؽ�Ʈ�ʵ���_����_�޾ƿ�_DAO��_INSERT�޼ҵ带_�����ϰ��ϴ�_�̺�Ʈ() {}
//	void ��ȸ��ư_�̺�Ʈ_������ȣ��_����Ʈ�ϰ�_��ȸ��ư��_������_������ȣ��_���õ�_������ȣ��_�Ű�������_SELECT�޼ҵ�_������_�ؽ�Ʈ�ʵ忡_���ð����_JTable��_���DB����_���() {}
//	void ������ư_�̺�Ʈ_JTable�ǰ���_���콺��_�����ϰų�_������ȣ��_��ȸ��_�ؽ�Ʈ�ǵ忡_�����ִ�_�����͸�����_DAO�޼ҵ����() {}

	UserDAO userDAO = new UserDAO();
	JTable table;
	ArrayList<UserDTO> list = new ArrayList<>();

	Gui() {
		// �����Ӽ���
		setTitle("��ǰ���� ��� ���α׷�");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//

		// ��
		JPanel labelJP = new JPanel(new GridLayout(4, 1));
		JLabel prNumLa = new JLabel("������ȣ");
		JLabel prNmaneLa = new JLabel("��ǰ��");
		JLabel prPriceLa = new JLabel("�ܰ�");
		JLabel prMaker = new JLabel("������");
//		labelJP.setBounds(0,0,150,500);
		labelJP.add(prNumLa);
		labelJP.add(prNmaneLa);
		labelJP.add(prPriceLa);
		labelJP.add(prMaker);
		labelJP.setVisible(true);
		//

		// �ؽ�Ʈ�ʵ�
		JPanel textJP = new JPanel(new GridLayout(4, 1));
//		ArrayList<UserDTO> list = userDAO.showArray();
//		Object[] row = new Object[1];
//		for (int i = 0; i < list.size(); i++) {
//			row[0] = list.get(i).getProbuctNum();
//		}
		String arr [] = {"1","2","3","4","5"};
		
		JComboBox<String> cb = new JComboBox<String>(arr); // �迭�� �޺��ڽ���
		JTextField prNameTxt = new JTextField();
		JTextField prPriceTxt = new JTextField();
		JTextField prMakerTxt = new JTextField();
		textJP.add(cb);
		textJP.add(prNameTxt);
		textJP.add(prPriceTxt);
		textJP.add(prMakerTxt);

		// ��ȸ���

		JPanel showJP = new JPanel(new BorderLayout()); // show ��ư Ŭ���� ���

		// ��ư�׷�
		JPanel btnJP = new JPanel();
		JButton inbtn = new JButton("���");
		JButton schbtn = new JButton("��ȸ");
		JButton delbtn = new JButton("����");
		btnJP.add(inbtn);
		btnJP.add(schbtn);
		btnJP.add(delbtn);

		//

		add(labelJP, BorderLayout.WEST);
		add(textJP, BorderLayout.CENTER);
		add(showJP, BorderLayout.EAST);
		add(btnJP, BorderLayout.SOUTH);

		//
		table = new JTable();
		table.setVisible(true);
		table.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] { "������ȣ", "��ǰ��", "�ܰ�", "������" }));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		table.setModel(model);
		showJP.add(new JScrollPane(table), BorderLayout.CENTER);

		// ��ư �̺�Ʈ
		inbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String prName = prNameTxt.getText();
				int prPrice = Integer.parseInt(prPriceTxt.getText());
				String prMaker = prMakerTxt.getText();
				userDAO.insert(prName, prPrice, prMaker);
			}
		});

		schbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				showUser();

				userDAO.search(cb.getItemAt(cb.getSelectedIndex()));
				prNameTxt.setText(userDAO.userDTO.getProductName());
				prPriceTxt.setText(Integer.toString(userDAO.userDTO.getPrice()));
				prMakerTxt.setText(userDAO.userDTO.getMaker());

			}
		});

	}

	//

	//

	public void showUser() {

		ArrayList<UserDTO> list = userDAO.showArray();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object[] row = new Object[4];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getProbuctNum();
			row[1] = list.get(i).getProductName();
			row[2] = list.get(i).getPrice();
			row[3] = list.get(i).getMaker();

			model.addRow(row);
		}
	}
}
