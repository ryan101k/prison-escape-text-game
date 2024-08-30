package textgame;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private String name;
    private String description;
    private Map<String, Location> exits = new HashMap<>();
    private Event event;
    private String storyHint;
    private Map<String, Runnable> actions = new HashMap<>();
    private NPC npc; // 추가: 위치에 있는 NPC

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addExit(String direction, Location location) {
        exits.put(direction, location);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setStoryHint(String hint) {
        this.storyHint = hint;
    }

    public void addAction(String actionName, Runnable action) {
        actions.put(actionName, action);
    }

    public void performAction(String actionName) {
        Runnable action = actions.get(actionName);
        if (action != null) {
            action.run();
        } else {
            System.out.println("해당 행동은 현재 위치에서 할 수 없습니다.");
        }
    }

    public String getDescription() {
        return description;
    }

    public Location getExit(String direction) {
        return exits.get(direction);
    }

    public String[] getAvailableExits() {
        return exits.keySet().toArray(new String[0]);
    }

    public String getName() {
        return name;
    }

    public Event getEvent() {
        return event;
    }

    public void showAvailableExits() {
        if (exits.isEmpty()) {
            System.out.println("이동할 수 있는 곳이 없습니다.");
        } else {
            System.out.println("이동 가능한 방향:");
            for (String direction : exits.keySet()) {
                System.out.println("- " + direction);
            }
        }
    }

    public void showStoryHint() {
        if (storyHint != null) {
            System.out.println("힌트: " + storyHint);
        }
    }

    public void showActions() {
        if (actions.isEmpty()) {
            System.out.println("여기서는 할 수 있는 행동이 없습니다.");
        } else {
            System.out.println("가능한 행동:");
            for (String actionName : actions.keySet()) {
                System.out.println("- " + actionName);
            }
        }
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public void interactWithNpc() {
        if (npc != null) {
            npc.interact();
        } else {
            System.out.println("이 위치에는 상호작용할 NPC가 없습니다.");
        }
    }
}
