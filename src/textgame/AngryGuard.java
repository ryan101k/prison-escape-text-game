package textgame;

import java.util.Scanner;

public class AngryGuard extends keepNpc {
    public AngryGuard(String name, String description) {
        super(name, description);
    }

    @Override
    public void interact() {
        System.out.println("화가 난 경비원과 상호작용합니다: " + getDescription());
        showDialogueOptions();
    }

    // 화가 난 경비원과의 대화 옵션을 보여주는 메서드
    @Override
    protected void showDialogueOptions() {
        Scanner scanner = new Scanner(System.in);
        boolean continueDialogue = true;

        while (continueDialogue) {
            System.out.println("어떻게 하시겠습니까?");
            System.out.println("1. 경비원에게 도움을 요청한다.");
            System.out.println("2. 경비원에게 사과한다.");
            System.out.println("3. 대화를 종료한다.");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    requestHelp(); // 도움 요청
                    continueDialogue = false;
                    break;
                case 2:
                    apologize(); // 사과
                    break;
                case 3:
                    System.out.println("대화를 종료합니다.");
                    continueDialogue = false;
                    break;
                default:
                    System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
                    break;
            }
        }
    }

    // 사과 메서드
    private void apologize() {
        System.out.println("경비원에게 진심으로 사과합니다.");
        System.out.println("화가 난 경비원: \"후... 사과는 받아들일게. 하지만 다음엔 조심해.\"");
        // 사과 후 호감도 약간 회복
        setAffection(getAffection() + 5);
        System.out.println("경비원의 호감도가 회복되었습니다. 현재 호감도: " + getAffection());
    }
}
