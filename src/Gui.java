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

//	private String 메인화면_상품명_단가_제조사_조회화면_등록버튼_조회버튼_삭제버튼이_들어간_화면구성;
//	private String 상품명;
//	private int 단가;
//	private String 제조사;
//	private String JTable;
//	void 등록버튼_이벤트_등록버튼을_누르면_각각의_텍스트필드의_값을_받아와_DAO의_INSERT메소드를_실행하게하는_이벤트() {}
//	void 조회버튼_이벤트_관리번호를_셀렉트하고_조회버튼을_누르면_관리번호에_선택된_관리번호를_매개변수로_SELECT메소드_실행후_텍스트필드에_선택값출력_JTable에_모든DB정보_출력() {}
//	void 삭제버튼_이벤트_JTable의값을_마우스로_선택하거나_관리번호로_조회해_텍스트피드에_값이있는_데이터를삭제_DAO메소드실행() {}

	UserDAO userDAO = new UserDAO();
	JTable table;
	ArrayList<UserDTO> list = new ArrayList<>();

	Gui() {
		// 프레임셋팅
		setTitle("제품정보 등록 프로그램");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//

		// 라벨
		JPanel labelJP = new JPanel(new GridLayout(4, 1));
		JLabel prNumLa = new JLabel("관리번호");
		JLabel prNmaneLa = new JLabel("상품명");
		JLabel prPriceLa = new JLabel("단가");
		JLabel prMaker = new JLabel("제조사");
//		labelJP.setBounds(0,0,150,500);
		labelJP.add(prNumLa);
		labelJP.add(prNmaneLa);
		labelJP.add(prPriceLa);
		labelJP.add(prMaker);
		labelJP.setVisible(true);
		//

		// 텍스트필드
		JPanel textJP = new JPanel(new GridLayout(4, 1));
//		ArrayList<UserDTO> list = userDAO.showArray();
//		Object[] row = new Object[1];
//		for (int i = 0; i < list.size(); i++) {
//			row[0] = list.get(i).getProbuctNum();
//		}
		String arr [] = {"1","2","3","4","5"};
		
		JComboBox<String> cb = new JComboBox<String>(arr); // 배열을 콤보박스로
		JTextField prNameTxt = new JTextField();
		JTextField prPriceTxt = new JTextField();
		JTextField prMakerTxt = new JTextField();
		textJP.add(cb);
		textJP.add(prNameTxt);
		textJP.add(prPriceTxt);
		textJP.add(prMakerTxt);

		// 조회출력

		JPanel showJP = new JPanel(new BorderLayout()); // show 버튼 클릭시 출력

		// 버튼그룹
		JPanel btnJP = new JPanel();
		JButton inbtn = new JButton("등록");
		JButton schbtn = new JButton("조회");
		JButton delbtn = new JButton("삭제");
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

		}, new String[] { "관리번호", "상품명", "단가", "제조사" }));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		table.setModel(model);
		showJP.add(new JScrollPane(table), BorderLayout.CENTER);

		// 버튼 이벤트
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
