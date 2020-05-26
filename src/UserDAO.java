
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

//	void JDBC_SQL연결() {}
//	void JDBC_SQL연결해제() {}
//	void 테이블에_입력받은_값을_넣는_메소드(){}
//	void DTO에_저장된_관리번호를_받아와_테이블에_삭제요청을_하는_메소드() {}
//	void DB에_저장된_정보를_관리번호를_통해_받아오는_메소드() {}
//	void DB에서_저장된_모든_정보를_받아와_JTable에_출력하고_최신화해주는_메소드() {}

public class UserDAO {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;

	Scanner sc = new Scanner(System.in);
	public UserDTO userDTO = new UserDTO();

	static final String JD = "com.mysql.jdbc.Driver";
	static final String DBURL = "jdbc:mysql://localhost/mydb";
	static final String dbID = "root";
	static final String dbPW = "root";

	public UserDAO() { // SQL 연결, 생성자이기 때문에 객체 생성시 호출
		try {
			Class.forName(JD);
			System.out.println("연결성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패");
		}
	}//

	public void connect() { // DB와 연결하는 메소드
		try {
			conn = DriverManager.getConnection(DBURL, dbID, dbPW);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect() { // 연결된 DB를 닫아주는 메소드
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}

	}

	public void insert(String productName, int productPrice, String productMaker) {
		connect();
		try {
			
			String insert;
			insert = "insert into product values(default,?,?,?)";
			pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, productName);
			pstmt.setInt(2, productPrice);
			pstmt.setString(3, productMaker);
			pstmt.executeUpdate(); // database 에 valuse 를 update! //

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			disconnect();
		}
	}

	public void delete(String productNum) {
		connect();
		try {
			String sqlDel;
			sqlDel = "delete from product where number ='" + productNum + "'";
			pstmt = conn.prepareStatement(sqlDel);
			pstmt.executeUpdate(); // database 에 valuse 를 update! //
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

//	public void update(String name, String number, String age, String phone) {
//		connect();
//		try {
//			String sql2;
//			sql2 = "UPDATE userDB SET name='" + name + "',age ='" + age + "' ,phone='" + phone + "' WHERE number = '"
//					+ number + "'";
//			pstmt = conn.prepareStatement(sql2);
//			pstmt.executeUpdate(); // database 에 valuse 를 update! //
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("수정실패. 입력하신 내용을 확인해 주세요");
//		} finally {
//			disconnect();
//		}
//	}

	public void search(String productNum) { // 로그인 메소드
		connect();
		try {
			stmt = conn.createStatement();
			String sql;
			sql = "select * from product where productNum = '" + productNum + "'"; // 내가 입력해준id 테이블값의 전체의
			ResultSet rs = stmt.executeQuery(sql); // 값을저장해준 모든정보를 결과 객체 rs에 담음
			if (rs.next()) {
				// 로그인 된 정보를 객체에 담아 SET 수정 파트에서 사용
				userDTO.setProbuctNum(rs.getString(1));
				userDTO.setProductName(rs.getString(2));
				userDTO.setPrice(rs.getInt(3));
				userDTO.setMaker(rs.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("호출실패");
		} finally {
			disconnect();
		}
	}

	public ArrayList<UserDTO> showArray() {
		connect();
		ArrayList<UserDTO> userlist = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			String sql;
			sql = "select * from product"; // 내가 입력해준id 테이블값의 전체의
			ResultSet rs = stmt.executeQuery(sql); // 값을저장해준 모든정보를 결과 객체 rs에 담음

			while (rs.next()) {
				UserDTO user = new UserDTO(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				userlist.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("호출실패");
		} finally {
			disconnect();
		}

		return userlist;

	}

//	public int idCheck(String idcheck) { // InputData 클래스에서 회원가입시 ID중복확인을 위해 호출되어 사용함
//		connect();
//		int result = 0;
//		try {
//			stmt = conn.createStatement();
//			String sqlIdcheck;
//			sqlIdcheck = "select number from userDB where number = '" + idcheck + "'"; // user_id 필드에 중복
//																						// 값이 있는지 확인
//			ResultSet rs = stmt.executeQuery(sqlIdcheck); //
//			if (rs.next()) {
//				if (idcheck.equals(rs.getString(1))) {
//					result = -1; // 중복값이 있을 때 -1을 반환값에 담아 오류가 있음을 알려준다
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		}
//		return result;
//	}
}
