package textgame;

import java.util.Scanner;

public class GameEngine {
    private Scanner scanner = new Scanner(System.in);
    private Location currentLocation;
    private Inventory inventory = new Inventory();
    protected boolean doorUnlocked = false;
    private keepNpc guard; // 간수 NPC

    public GameEngine(Location startLocation, keepNpc guard) {
        this.currentLocation = startLocation;
        this.guard = guard; // 간수 NPC 초기화
    }

    public void startChapter() {
        String command = "";

        System.out.println("게임을 시작합니다!");
        Help.showHelp(); // 초기 도움말 보기
        look(); // 초기 상태 살펴보기

        while (!command.equals("종료") && !isCompleted()) {
            System.out.print("> ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "도움말":
                    Help.showHelp();
                    break;
                case "살펴보기":
                    look();
                    break;
                case "이동":
                    move();
                    break;
                case "인벤토리":
                    inventory.showInventory();
                    break;
                case "사용":
                    useItem();
                    break;
                case "행동하기":
                    showOptions();
                    break;
                case "종료":
                    System.out.println("게임을 종료합니다. 감사합니다!");
                    break;
                default:
                    System.out.println("알 수 없는 명령어입니다. '도움말'을 입력하면 명령어 목록을 볼 수 있습니다.");
                    break;
            }
        }
        scanner.close();
    }

    protected void look() {
        System.out.println(currentLocation.getDescription());
        currentLocation.showAvailableExits();
        currentLocation.showStoryHint();
    }

    protected void move() {
        if ("복도".equals(currentLocation.getName()) && !doorUnlocked) {
            System.out.println("문이 잠겨 있어 이동할 수 없습니다.");
            return;
        }

        System.out.println("어느 방향으로 이동하시겠습니까? (예: 북쪽)");
        String direction = scanner.nextLine().trim();

        Location nextLocation = currentLocation.getExit(direction);
        if (nextLocation != null) {
            if ("감옥".equals(currentLocation.getName()) && "복도".equals(nextLocation.getName()) && !doorUnlocked) {
                System.out.println("문이 잠겨 있어 나갈 수 없습니다. 문을 열 방법을 찾아야 합니다.");
                return;
            }
            currentLocation = nextLocation;
            System.out.println("이동 중... 현재 위치: " + currentLocation.getName());
            look(); // 이동 후 자동으로 주변을 살펴봄
        } else {
            System.out.println("그 방향으로는 이동할 수 없습니다.");
        }
    }

    protected void useItem() {
        if ("감옥".equals(currentLocation.getName()) && inventory.hasItem("낡은 나무 조각")) {
            System.out.println("낡은 나무 조각을 사용하여 감옥 문을 엽니다.");
            inventory.removeItem("낡은 나무 조각");
            
            doorUnlocked = true;

            // 복도로 나가는 출구를 설정
            Location corridor = currentLocation.getExit("북쪽");
            if (corridor != null) {
                corridor.addExit("남쪽", currentLocation); // 감옥에서 복도로 이동 가능하도록 설정
            }

            // 경비원과의 이벤트 처리
            guard.handleEscapeAttempt(); 

        } else {
            System.out.println("여기서는 사용할 아이템이 없습니다.");
        }
    }

    protected void showOptions() {
        System.out.println("현재 위치에서 가능한 행동:");
        currentLocation.showActions();

        System.out.print("선택하세요: ");
        String choice = scanner.nextLine().trim();

        currentLocation.performAction(choice);
    }

    protected void triggerEvent() {
        Event event = currentLocation.getEvent();
        if (event != null) {
            event.trigger();
        } else {
            System.out.println("현재 위치에서 발생할 이벤트가 없습니다.");
        }
    }

    public boolean isCompleted() {
        return false; // 서브클래스에서 구현
    }

    protected Scanner getScanner() {
        return scanner;
    }

    protected Inventory getInventory() {
        return inventory;
    }

    protected Location getCurrentLocation() {
        return currentLocation;
    }

    protected void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    protected boolean isDoorUnlocked() {
        return doorUnlocked;
    }

    protected void setDoorUnlocked(boolean doorUnlocked) {
        this.doorUnlocked = doorUnlocked;
    }
}
