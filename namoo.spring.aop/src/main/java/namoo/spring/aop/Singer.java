package namoo.spring.aop;

public class Singer implements Performer {
	private String name = "Someone";
	private Song song;

	public Singer() {
	}

	public Singer(Song song) {
		this.song = song;
	}

	public Singer(String name, Song song) {
		this.name = name;
		this.song = song;
	}

	@Override
	public void perform() {
		System.out.println(name + " is singing. " + song.getTitle());
//		throw new RuntimeException("Something wrong!");
	}
}