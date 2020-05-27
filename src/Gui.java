import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.TableModel;

public class Gui extends JFrame implements ActionListener {

//	private String ����ȭ��_��ǰ��_�ܰ�_������_��ȸȭ��_��Ϲ�ư_��ȸ��ư_������ư��_��_ȭ�鱸��;
//	private String ��ǰ��;
//	private int �ܰ�;
//	private String ������;
//	private String JTable;
//	void ��Ϲ�ư_�̺�Ʈ_��Ϲ�ư��_������_������_�ؽ�Ʈ�ʵ���_����_�޾ƿ�_DAO��_INSERT�޼ҵ带_�����ϰ��ϴ�_�̺�Ʈ() {}
//	void ��ȸ��ư_�̺�Ʈ_������ȣ��_����Ʈ�ϰ�_��ȸ��ư��_������_������ȣ��_���õ�_������ȣ��_�Ű�������_SELECT�޼ҵ�_������_�ؽ�Ʈ�ʵ忡_���ð����_JTable��_���DB����_���() {}
//	void ������ư_�̺�Ʈ_JTable�ǰ���_���콺��_�����ϰų�_������ȣ��_��ȸ��_�ؽ�Ʈ�ǵ忡_�����ִ�_�����͸�����_DAO�޼ҵ����() {}

	UserDAO userDAO = new UserDAO();
	UserDTO userDTO = new UserDTO();
	JTable table;
	JComboBox<String> cb;

	JTextField prNameTxt;
	JTextField prPriceTxt;
	JTextField prMakerTxt;
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
		//
		ArrayList<Object[]> list = userDAO.showArray();
		ArrayList<String> cblist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Object[] row = new Object[4];
			row = list.get(i);

			cblist.add((String) row[0]);
		}

		// arraylist�� �迭��
		String arr[] = new String[cblist.size()];
		int size = 0;
		for (String temp : cblist) {
			arr[size++] = temp;
		}
		//
		cb = new JComboBox<String>(arr); // �迭�� �޺��ڽ���

		prNameTxt = new JTextField();
		prPriceTxt = new JTextField();
		prMakerTxt = new JTextField();
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
		
	

		showJP.add(new JScrollPane(table), BorderLayout.CENTER);

		// ��ư �̺�Ʈ
		// ��Ϲ�ư

		inbtn.addActionListener(this);
		schbtn.addActionListener(this);
		delbtn.addActionListener(this);
	}

	//

	public void showUser() { // DB���� ���̺�� ������

		ArrayList<Object[]> list = userDAO.showArray();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		for (int i = 0; i < list.size(); i++) {
			Object[] row = new Object[4];
			row = list.get(i);

			model.addRow(row);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("���")) {
			insert();
		} else if (e.getActionCommand().equals("��ȸ")) {
			search();
		} else {
			delete();
		}
	}

	void insert() {

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // ���̺� �� �� �ʱ�ȭ
		showUser();

		userDTO.setProductName(prNameTxt.getText());
		userDTO.setPrice(Integer.parseInt(prPriceTxt.getText()));
		userDTO.setMaker(prMakerTxt.getText());

		userDAO.insert(userDTO); // set���� ���� ��� ��ü�� �Ű������� ��Ƽ� �ѱ��.

		prNameTxt.setText("");
		prPriceTxt.setText("");
		prMakerTxt.setText("");
	}

	void search() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // ���̺� �� �� �ʱ�ȭ
		showUser();

		userDTO.setProductNum(cb.getItemAt(cb.getSelectedIndex()));
		userDAO.search(userDTO); // �޺��ڽ� �� ��������
		prNameTxt.setText(userDAO.userDTO.getProductName());
		prPriceTxt.setText(Integer.toString(userDAO.userDTO.getPrice()));
		prMakerTxt.setText(userDAO.userDTO.getMaker());

	}

	void delete() {

		userDTO.setProductNum(cb.getItemAt(cb.getSelectedIndex()));
		userDAO.delete(userDTO);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		showUser();
	}

}
