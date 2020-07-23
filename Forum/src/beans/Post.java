package beans;

public class Post {

	private int id;
	private User owner;
	private String subject;
	private String desc;
	private String content;
	private int likes;
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(int id, User owner, String subject, String desc, String content, int likes) {
		super();
		this.id = id;
		this.owner = owner;
		this.subject = subject;
		this.desc = desc;
		this.content = content;
		this.likes = likes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
}
