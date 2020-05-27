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

//	private String 메인화면_상품명_단가_제조사_조회화면_등록버튼_조회버튼_삭제버튼이_들어간_화면구성;
//	private String 상품명;
//	private int 단가;
//	private String 제조사;
//	private String JTable;
//	void 등록버튼_이벤트_등록버튼을_누르면_각각의_텍스트필드의_값을_받아와_DAO의_INSERT메소드를_실행하게하는_이벤트() {}
//	void 조회버튼_이벤트_관리번호를_셀렉트하고_조회버튼을_누르면_관리번호에_선택된_관리번호를_매개변수로_SELECT메소드_실행후_텍스트필드에_선택값출력_JTable에_모든DB정보_출력() {}
//	void 삭제버튼_이벤트_JTable의값을_마우스로_선택하거나_관리번호로_조회해_텍스트피드에_값이있는_데이터를삭제_DAO메소드실행() {}

	UserDAO userDAO = new UserDAO();
	UserDTO userDTO = new UserDTO();
	JTable table;
	JComboBox<String> cb;

	JTextField prNameTxt;
	JTextField prPriceTxt;
	JTextField prMakerTxt;
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
		//
		ArrayList<Object[]> list = userDAO.showArray();
		ArrayList<String> cblist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Object[] row = new Object[4];
			row = list.get(i);

			cblist.add((String) row[0]);
		}

		// arraylist를 배열로
		String arr[] = new String[cblist.size()];
		int size = 0;
		for (String temp : cblist) {
			arr[size++] = temp;
		}
		//
		cb = new JComboBox<String>(arr); // 배열을 콤보박스로

		prNameTxt = new JTextField();
		prPriceTxt = new JTextField();
		prMakerTxt = new JTextField();
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
		
	

		showJP.add(new JScrollPane(table), BorderLayout.CENTER);

		// 버튼 이벤트
		// 등록버튼

		inbtn.addActionListener(this);
		schbtn.addActionListener(this);
		delbtn.addActionListener(this);
	}

	//

	public void showUser() { // DB내용 테이블로 보내기

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

		if (e.getActionCommand().equals("등록")) {
			insert();
		} else if (e.getActionCommand().equals("조회")) {
			search();
		} else {
			delete();
		}
	}

	void insert() {

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // 테이블 모델 값 초기화
		showUser();

		userDTO.setProductName(prNameTxt.getText());
		userDTO.setPrice(Integer.parseInt(prPriceTxt.getText()));
		userDTO.setMaker(prMakerTxt.getText());

		userDAO.insert(userDTO); // set으로 값이 담긴 객체를 매개변수로 담아서 넘긴다.

		prNameTxt.setText("");
		prPriceTxt.setText("");
		prMakerTxt.setText("");
	}

	void search() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // 테이블 모델 값 초기화
		showUser();

		userDTO.setProductNum(cb.getItemAt(cb.getSelectedIndex()));
		userDAO.search(userDTO); // 콤보박스 값 가져오기
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
