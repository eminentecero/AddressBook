package AddressBook;

import java.io.*;

public class Person {
	private String name;
	private String phone;
	private String address;
	private String mail;

	Person() {
		name = null;
		phone = null;
		address = null;
		mail = null;
	}

	// 객체 생성자
	Person(String name, String phone, String address, String mail) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.mail = mail;
	}

	// 이름
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 휴대번호
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// 주소
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// 이메일 주소
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	// 반환값 설정
	public String toString() {
		return "\n 이름 : " + this.name + "\n 전화번호 : " + this.phone + "\n 주소 : " + this.address + "\n 이메일 주소 : "
				+ this.mail + "\n";
	}

	// 파일에다가 객체 정보를 입력하게 하는 함수
	public void WriteMyFile(DataOutputStream out) throws IOException {
		// 파일에 해당 내용을 쓴다.
		out.writeUTF(this.name);
		out.writeUTF(this.phone);
		out.writeUTF(this.address);
		out.writeUTF(this.mail);
	}

	public void readMyFile(DataInputStream in) throws Exception {
		// 저장된 데이터 값 불러와서 각 메소드에 setter로 설정하기
		this.setName(in.readUTF());
		this.setPhone(in.readUTF());
		this.setAddress(in.readUTF());
		this.setMail(in.readUTF());
	}
}