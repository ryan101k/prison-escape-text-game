package textgame;

import java.util.Scanner;

public class keepNpc extends NPC {
    private static final int DANGER_THRESHOLD = 5; // 호감도 위험 임계값
    private static Scanner scanner = new Scanner(System.in);
    private Inventory inventory = new Inventory();
    
    public keepNpc(String name, String description) {
        super(name, description);
    }

    @Override
    public void interact() {
        System.out.println("경비원과 상호작용합니다: " + getDescription());
        showDialogueOptions();
    }

    // 경비원과의 대화 옵션을 보여주는 메서드
    @Override
    protected void showDialogueOptions() {
        boolean continueDialogue = true;

        while (continueDialogue) {
            // 호감도에 따른 경비원의 반응
            if (getAffection() >= 20) {
                System.out.println("(경비원이 친절하게 미소를 지으며 말합니다.)");
            } else if (getAffection() >= 10) {
                System.out.println("(경비원이 불편한 표정을 짓고 있습니다.)");
            } else if (getAffection() > 0) {
                System.out.println("(경비원이 냉담하게 당신을 바라봅니다.)");
            } else {
                System.out.println("(경비원이 화가 나서 위협적으로 다가옵니다.)");
                guardAttacksPlayer();
            }

            System.out.println("어떻게 하시겠습니까?");
            System.out.println("1. 경비원을 설득해 보자.");
            System.out.println("2. 경비원을 도발해 보자.");
            System.out.println("3. 경비원에게 도움을 요청하자.");
            System.out.println("4. 그냥 무시한다.");
            System.out.println("5. 대화를 종료한다.");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    persuadeGuard();
                    continueDialogue = false;
                    break;
                case 2:
                    provokeGuard();
                    break;
                case 3:
                    requestHelp();
                    break;
                case 4:
                    ignoreGuard();
                    continueDialogue = false;
                    break;
                case 5:
                    System.out.println("대화를 종료합니다.");
                    continueDialogue = false;
                    break;
                default:
                    System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
                    break;
            }
        }
    }

    // 경비원을 설득하는 시도
    private void persuadeGuard() {
        System.out.println("당신은 진심으로 경비원에게 사정을 이야기하며 당신이 잘못된 일이 없다고 설명합니다.");
        setAffection(getAffection() - 5); // 호감도 감소
        System.out.println("경비원: \"하! 누구나 자기 상황을 변명하려 하겠지. 하지만 상황은 별로 나아지지 않을 거야.\"");
        System.out.println("경비원이 당신을 보고 조소하며 떠납니다.");
        System.out.println("경비원의 호감도가 감소했습니다. 현재 호감도: " + getAffection());
    }

    // 경비원을 도발하는 시도
    private void provokeGuard() {
        System.out.println("당신은 경비원을 도발하여 그의 분노를 일으킵니다.");
        setAffection(getAffection() - 10); // 호감도 감소
        System.out.println("경비원: \"너 감히 나를 도발한다고? 이봐, 내가 경고했어.\"");
        System.out.println("경비원이 노한 상태로 당신을 바라봅니다.");
        System.out.println("경비원의 호감도가 감소했습니다. 현재 호감도: " + getAffection());

        // 호감도가 위험 임계값 이하일 때 경비원이 공격하여 게임 종료
        if (getAffection() <= DANGER_THRESHOLD) {
            guardAttacksPlayer();
        }
    }

    // 경비원에게 도움을 요청하는 시도
    protected void requestHelp() {
        System.out.println("당신은 경비원에게 상황을 설명하고 도움을 요청합니다.");
        if (getAffection() >= 20) {
            System.out.println("경비원: \"좋아, 내가 도와줄게. 여기 열쇠를 줄 테니 탈출해.\"");
            // 인벤토리에 열쇠 추가하는 로직 추가
      
        } else {
            System.out.println("경비원: \"현재로서는 도와줄 이유가 없어. 좀 더 신경 써봐야겠어.\"");
        }
    }

    // 경비원을 무시할 시 발생하는 메서드
    private void ignoreGuard() {
        System.out.println("경비원: \"나를 부르고 무시하다니? 정말 무례하군.\"");
        setAffection(getAffection() - 10); // 호감도 감소
        System.out.println("경비원의 호감도가 감소했습니다. 현재 호감도: " + getAffection());
    }

    // 경비원이 플레이어를 공격하여 게임을 종료하는 메서드
    private void guardAttacksPlayer() {
        System.out.println("경비원: \"내가 경고했지! 이건 네가 자초한 일이야!\"");
        System.out.println("경비원이 당신을 공격했습니다. 당신은 치명상을 입고 쓰러졌습니다.");
        System.out.println("게임 오버! 경비원에게 공격당해 목숨을 잃었습니다.");
        System.exit(0); // 프로그램을 종료합니다.
    }

    // 탈출 시도를 처리하는 메서드
    public void handleEscapeAttempt() {
        System.out.println("경비원이 당신의 탈출 시도를 감지했습니다!");
        setAffection(getAffection() - 10); // 호감도 감소
        System.out.println("경비원: \"탈출하려고 하는군? 조심해라.\"");
        System.out.println("경비원의 호감도가 감소했습니다. 현재 호감도: " + getAffection());

        if (getAffection() <= DANGER_THRESHOLD) {
            guardAttacksPlayer(); // 호감도가 위험 임계값 이하일 때 공격
        }
    }
}
