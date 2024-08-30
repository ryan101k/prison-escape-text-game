package textgame;

public abstract class NPC {
    private String name;
    private String description;
    private int affection; // 호감도

    public NPC(String name, String description) {
        this.name = name;
        this.description = description;
        this.affection = 20; // 초기 호감도
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAffection() {
        return affection;
    }

    public void setAffection(int affection) {
        this.affection = affection;
    }

    public abstract void interact(); // 상호작용 메서드

	protected void showDialogueOptions() {
		// TODO Auto-generated method stub
		
	}
}
