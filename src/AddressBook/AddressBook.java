package AddressBook;

import java.io.*;

public class AddressBook {
	// 생성자 함수에다가 외부에서 크기 정하는 걸로
	private Person[] person;
	int addindex = 0; // 인원수를 추가할 때 필요한 인덱스 설정

	// Person 배열에 동적 할당
	public AddressBook(int Number) {
		person = new Person[Number];

		DataInputStream in = null;

		File file = new File("Person.dat");
		try {
			// 파일이 존재하는지 검사!
			// 존재한다면?!
			if (!file.exists()) {
				file.createNewFile();
			}
			// Person.dat에 저장되어있는 데이터를 읽어오는 in 객체 생성
			in = new DataInputStream(new FileInputStream("Person.dat"));
			readFile(in);


		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// in 파일을 닫음
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// Person 배열 getter 함수
	public Person getPerson(int index) {
		return person[index];
	}

	// 주소록 인원을 추가하기 위해서 Person p 객체에 정보를 입력 받아서 추가
	public void add(Person p) throws Exception {
		if (addindex != person.length)
			// 처음에 입력 후에도 추가로 입력할 사람들의 인덱스 고려
			person[addindex++] = p;
		else
			// 용량 초과되었을 때 익셉션 발송
			throw new Exception("용량이 부족합니다.");
	}

	// 찾는 사람 이름을 입력하면 몇 번째 인덱스에 있는지 반환
	public int find(String key) throws Exception {
		for (int i = 0; i < addindex; i++) {
			// i 배열의 name 정보와 key가 같다면?!
			if (getPerson(i).getName().equals(key)) {
				return i; // 해당 인덱스 바로 반환
			}
		}
		// key가 존재하지 않을때
		throw new Exception("해당 주소록이 없습니다.");

	}

	// 수정할 번쨰(index)를 입력하면 Person P에다가 정보를 받아서 배열에 입력
	public void edit(int index, Person p) {
		// index번째 배열에 객체 p 넣어서 수정
		person[index] = p;
	}

	// 삭제할번째(index)를 입력하면 해당 배열 삭제
	public void delete(int index) {
		// 삭제하려는 index가 첫번째~마지막 앞 인덱스 일 때
		// 변경하려는 인덱스부터 마지막 앞 인덱스까지 앞으로 한칸씩 당기기
		for (int i = index; i < addindex - 1; i++) {
			person[i] = person[i + 1];
		}
		// 삭제하려는 index가 마지막 인덱스 일 때
		// 맨 마지막 인덱스 삭제
		addindex--;
	}

	// 파일에다가 person 객체를 입력하게 하는 함수
	public void writeFile(DataOutputStream out) throws IOException {
		out.writeInt(addindex);
		for (int i = 0; i < addindex; i++) {
			person[i].WriteMyFile(out);
		}
	}

	public void readFile(DataInputStream in) throws Exception {
		// 파일에서 저장된 객체 수 정보 불러와서 addindex에 저장
		addindex = in.readInt();

		// 저장된 객체 수 만큼 for구문 돌리기
		for (int i = 0; i < addindex; i++) {
			// 새로운 Person 객체 생성 후 readMyFile에서 요소 설정하기
			Person p = new Person();
			p.readMyFile(in);
			// 불러온 값이 할당된 p를 person 배열 안에다가 순서대로 집어넣기
			person[i] = p;

		}
	}
}