package mail;


public class Student {
	private String no;
	private String name;
	private int groupNo;

	@Override
	public String toString() {
		return "Student [no=" + no + ", name=" + name + ", groupNo=" + groupNo + "]";
	}

	public Student(String no, String name, int groupNo) {
		super();
		this.no = no;
		this.name = name;
		this.groupNo = groupNo;
	}


	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

}
