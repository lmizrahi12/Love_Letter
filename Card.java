
public class Card {
	private int value;
	private String name;
	private String effect;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	
	public Card(int value, String name, String effect) {
		this.value = value;
		this.name = name;
		this.effect = effect;
	}
	
	public Card(int value, String name) {
		this.value = value;
		this.name = name;
		this.effect = "No effect message.";
	}
	
	public Card clone(){
		Card card2 = new Card(this.value, this.name, this.effect);
		return card2;
	}
	
}
