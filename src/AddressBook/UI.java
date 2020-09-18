package AddressBook;

import java.io.*;
import java.util.Scanner;

public class UI {

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		String name; // 이름
		String phone; // 전화번호
		String address; // 주소
		String mail; // 이메일 주소
		String key; // 찾기 기능 - 찾고싶은 사람 이름 입력
		int index; // 찾기 기능에서 검색 후 찾은 사람 몇번째인지 반환
		DataOutputStream out = null;

		// AddressBook 클래스 추가
		AddressBook ab = new AddressBook(100);

		while (true) // 반복문
		{
			Menu.menu(); // 메뉴를 보여준다.
			int answer = scan.nextInt(); // 번호를 입력받는다.
			scan.nextLine();
			switch (answer) // 번호에 따른 메소드 실행
			{
			case 1: // 입력하기 add
				try {
					System.out.print("\n"); // 가독성을 위한 줄 바꿈

					// 사용자에게 입력값 받기
					System.out.print("이름을 입력하세요 : ");
					name = scan.nextLine();
					System.out.print("전화번호를 입력하세요 : ");
					phone = scan.nextLine();
					System.out.print("주소를 입력하세요 : ");
					address = scan.nextLine();
					System.out.print("이메일 주소를 입력하세요 : ");
					mail = scan.nextLine();

					// 중복 검사하기 - 같은 주소에 살 가능성 염두해두고 주소지 제외 다 중복 검사
					for (int j = 0; j < ab.addindex; j++) {
						if (name.equals(ab.getPerson(j).getName()) || phone.equals(ab.getPerson(j).getPhone())
								|| mail.equals(ab.getPerson(j).getMail())) {
							throw new Exception("!)중복된 정보가 있습니다.\n이름과 전화번호를 정확하게 입력해주시거나 수정하기를 이용해주십시오.");
						}
					}

					// Person 객체 생성해서 해당 객체에 모든 정보 입력
					Person p = new Person(name, phone, address, mail);

					// AddressBook에서 추가하기 기능 실행
					ab.add(p);
				} catch (Exception e) {
					// throw Exception에 있는 메세지 읽어오기
					String str = e.getMessage();
					System.out.println(str);
				}
				break;

			case 2: // 검색하기
				// 검색할 값을 받기(이름)
				System.out.print("이름을 입력하세요 : ");
				key = scan.nextLine();
				try {
					// AddressBook에 있는 검색하기 기능 실행
					// 찾은 사람 인데스를 할당
					index = ab.find(key);
					System.out.println(ab.getPerson(index));
				} catch (Exception e) {
					// throw Exception에 있는 메세지 읽어오기
					String str = e.getMessage();
					System.out.println(str);
				}

				break;
			case 3: // 수정하기
				// ************수정받는 정보가 기존에 있는 배열 중에 포함된 배열 없는지, 있으면 익셉션 처리하도록 설정
				System.out.print("이름을 입력하세요 : ");
				key = scan.nextLine();
				index = 0;
				try {
					// AddressBook에 있는 검색하기 기능 실행
					// 만약 이름으로 find했는데 그게 없다면? 익셉션 처리 & 찾은 사람 인데스를 할당
					index = ab.find(key);
					System.out.print("\n"); // 가독성을 위한 줄 바꿈

					// 사용자에게 입력값 받기
					System.out.print("이름을 입력하세요 : ");
					name = scan.nextLine();
					System.out.print("전화번호를 입력하세요 : ");
					phone = scan.nextLine();
					System.out.print("주소를 입력하세요 : ");
					address = scan.nextLine();
					System.out.print("이메일 주소를 입력하세요 : ");
					mail = scan.nextLine();

					// 이미 존재하는 배열 중에 같은 데이터를 가진 배열이 존재하는지 검사
					for (int i = 0; i < ab.addindex; i++) {
						if (name.equals(ab.getPerson(i).getName()) && phone.equals(ab.getPerson(i).getPhone())
								&& address.equals(ab.getPerson(i).getAddress())
								&& mail.equals(ab.getPerson(i).getMail())) {
							throw new Exception("중복된 데이터가 이미 존재합니다.");
						}

					}
					// Person 객체 생성해서 해당 객체에 모든 정보 입력
					Person p = new Person(name, phone, address, mail);

					// AddressBook에서 추가하기 기능 실행
					ab.edit(index, p);

				} catch (Exception e) {
					// find에서 이름값이 존재하지 않을 경우
					String str = e.getMessage();
					System.out.println(str);
					continue;
				}
				break;

			case 4: // 삭제하기
				index = -1;
				System.out.print("이름을 입력하세요 : ");
				key = scan.nextLine();
				try {
					// AddressBook에 있는 검색하기 기능 실행
					// 만약 이름으로 find했는데 그게 없다면? 익셉션 처리 & 찾은 사람 인데스를 할당
					index = ab.find(key);
					System.out.println("삭제 되었습니다.");
				} catch (Exception e) {
					// find에서 이름값이 존재하지 않을 경우
					String str = e.getMessage();
					System.out.println(str);
					continue;
				}

				ab.delete(index);

				break;
			case 5: // 전체 출력하기
				// 주소록이 비었을 때 출력문 작성
				if (ab.addindex == 0)
					System.out.println("주소록이 비었습니다.");
				else
					for (int i = 0; i < ab.addindex; i++) {
						System.out.println(ab.getPerson(i));
					}

				break;
			case 6:
				// 배열 파일로 저장
				try {
					// 파일에 배열 내용을 입력할 out 객체 생성 - 빠른 실행을 위해 BufferedOutputStream 실행
					out = new DataOutputStream(new FileOutputStream("Person.dat"));
					// AddressBook 클래스에서 writeFile 함수 실행
					ab.writeFile(out);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException ioe) {
					System.out.println("파일로 출력할 수 없습니다.");
				} finally {
					try {
						// out 파일을 닫음
						out.close();
						System.out.println("저장이 완료되었습니다.");
					} catch (Exception e) {
					}
				}
				break;

			case 7: // 프로그램 종료
				break;
			}
			// 프로그램 종료시 출력
			if (answer == 7) {
				System.out.println("시스템이 종료됩니다.");
				break;
			}
		}
	}
}