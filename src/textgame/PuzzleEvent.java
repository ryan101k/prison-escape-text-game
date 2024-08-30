package textgame;

import java.util.Scanner;

public class PuzzleEvent implements Event {
    private String puzzleDescription;
    private String correctAnswer;

    public PuzzleEvent(String puzzleDescription, String correctAnswer) {
        this.puzzleDescription = puzzleDescription;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void trigger() {
        System.out.println(puzzleDescription);
        System.out.print("답을 입력하세요: ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine().trim();
        if (answer.equals(correctAnswer)) {
            System.out.println("퍼즐을 해결했습니다!");
        } else {
            System.out.println("퍼즐이 틀렸습니다. 다시 시도해보세요.");
        }
    }
}
