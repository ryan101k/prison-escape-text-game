package textgame;

public class CellChapter extends GameEngine implements Chapter {
    private keepNpc guard; // 일반 경비원 NPC
    private AngryGuard angryGuard; // 화가 난 경비원 NPC
    private boolean isAngryGuardPresent = false; // 화가 난 경비원이 현재 위치에 있는지 여부

    public CellChapter() {
        super(new Location("감옥", "당신은 차가운 감옥에 갇혀 있습니다. 문은 잠겨 있고, 벽에는 낡은 쇠창살이 있습니다."), 
              new keepNpc("경비원", "경비원이 감옥을 순찰하고 있습니다. 그와 대화를 시도할 수 있습니다."));
        guard = new keepNpc("경비원", "경비원이 감옥을 순찰하고 있습니다. 그와 대화를 시도할 수 있습니다.");
        angryGuard = new AngryGuard("화가 난 경비원", "화가 난 경비원이 감옥에 나타났습니다! 그와 대화를 시도할 수 있습니다.");
        initializeChapter();
    }

    @Override
    public void startChapter() {
        System.out.println("챕터가 시작되었습니다. 감옥에서 탈출하기 위해 명령어를 입력하세요.");
        Help.showHelp(); // Help 클래스를 사용하여 도움말을 표시합니다.
        super.startChapter(); // 기존의 startChapter 호출
        if (isCompleted()) {
            System.out.println("감옥에서 탈출했습니다! 다음 챕터로 넘어가세요.");
            // 다음 챕터로 넘어가는 로직 추가
        }
    }

    @Override
    public boolean isCompleted() {
        return "정원".equals(getCurrentLocation().getName()) && getInventory().hasItem("열쇠");
    }

    private void initializeChapter() {
        Location cell = getCurrentLocation();
        Location corridor = new Location("복도", "문이 열리면 복도로 나올 수 있습니다. 복도는 어둡고 길게 이어져 있습니다.");
        Location hiddenRoom = new Location("숨겨진 방", "숨겨진 방은 어두운 곳에 있으며, 바닥에 흩어져 있는 열쇠가 보입니다.");
        Location garden = new Location("정원", "정원은 밝고 평화로운 곳입니다. 정원의 문이 열려 있습니다.");

        // 위치 간 이동 설정
        cell.addExit("북쪽", corridor);
        corridor.addExit("남쪽", cell);
        corridor.addExit("동쪽", hiddenRoom);
        hiddenRoom.addExit("서쪽", corridor);
        hiddenRoom.addExit("남쪽", garden);
        garden.addExit("북쪽", hiddenRoom);

        // 이벤트 설정
        cell.setEvent(new ItemAcquisitionEvent("감옥의 벽에서 낡은 나무 조각을 발견했습니다. 이를 사용하여 문을 열어 탈출할 수 있을 것입니다.", "낡은 나무 조각", getInventory()));
        hiddenRoom.setEvent(new PuzzleEvent("숨겨진 방에서 나가기 위해 퍼즐을 풀어야 합니다. 퍼즐의 힌트는 '열쇠의 숫자'입니다.", "1234"));

        // 스토리 힌트 설정
        cell.setStoryHint("벽을 잘 살펴보면 유용한 물건을 찾을 수 있을지도 모릅니다. 주변을 자세히 살펴보세요.");
        corridor.setStoryHint("복도로 나가면 탈출의 기회가 있을 수 있습니다. 복도에서 주변을 확인해 보세요.");
        hiddenRoom.setStoryHint("숨겨진 방에서 중요한 아이템을 찾아야 합니다. 바닥을 잘 살펴보세요.");
        garden.setStoryHint("정원에 도착하면 탈출이 거의 완료된 것입니다. 정원을 조사하여 최종 단계를 준비하세요.");

        // 액션 설정
        cell.addAction("주변을 살펴본다", () -> triggerEvent());
        cell.addAction("주머니에 있는 아이템을 사용한다", () -> useItem());
        cell.addAction("간수를 불러보자", () -> callGuard()); // 간수 부르는 이벤트 추가

        corridor.addAction("이벤트 발생시키기", () -> triggerEvent());
        corridor.addAction("아이템 사용하기", () -> useItem());

        hiddenRoom.addAction("퍼즐 풀기", () -> triggerEvent()); // 퍼즐 이벤트 발생
        hiddenRoom.addAction("아이템 사용하기", () -> useItem());

        garden.addAction("이벤트 발생시키기", () -> triggerEvent()); // 정원에서는 이벤트 발생 가능
        garden.addAction("아이템 사용하기", () -> useItem());

        setCurrentLocation(cell); // 게임 시작 위치
    }

    private void callGuard() {
        if (isAngryGuardPresent) {
            System.out.println("화가 난 경비원과 마주합니다.");
            angryGuard.interact(); // 화가 난 경비원과의 상호작용 시작
        } else {
            System.out.println("경비원을 불렀습니다. 경비원이 다가옵니다...");
            guard.interact(); // 일반 경비원과의 상호작용 시작
        }
    }

    @Override
    protected void useItem() {
        if ("감옥".equals(getCurrentLocation().getName()) && getInventory().hasItem("낡은 나무 조각")) {
            System.out.println("낡은 나무 조각을 사용하여 감옥 문을 열려고 시도 합니다");
            getInventory().removeItem("낡은 나무 조각");
            System.out.println("나무 조각이 부러지고 소음이 납니다 [나무조각이 파괴되었습니다]");
//            doorUnlocked = true;
//
//            // 복도로 나가는 출구를 설정
//            Location corridor = getCurrentLocation().getExit("북쪽");
//            if (corridor != null) {
//                corridor.addExit("남쪽", getCurrentLocation()); // 감옥에서 복도로 이동 가능하도록 설정
//            }
            isAngryGuardPresent=true;
            System.out.println("경비원이 분노하여 이성을 잃어버렸습니다");        
            System.out.println("화가 난 경비원이 나타났습니다! 호감도가 감소합니다.");
            angryGuard.handleEscapeAttempt();               
        } else {
            System.out.println("여기서는 사용할 아이템이 없습니다.");
        }
    }

    public void setAngryGuardPresent(boolean isPresent) {
        isAngryGuardPresent = isPresent;
    }
}
