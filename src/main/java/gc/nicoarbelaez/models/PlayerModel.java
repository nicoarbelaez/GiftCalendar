package gc.nicoarbelaez.models;

import java.util.List;
import java.util.Map;

public class PlayerModel {
    private String uuid;
    private Map<String, List<Integer>> calendarRewardsRegister;

    public PlayerModel(String uuid, Map<String, List<Integer>> calendarRewardsRegister) {
        this.uuid = uuid;
        this.calendarRewardsRegister = calendarRewardsRegister;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<String, List<Integer>> getCalendarRewardsRegister() {
        return calendarRewardsRegister;
    }

    public void setCalendarRewardsRegister(Map<String, List<Integer>> calendarRewardsRegister) {
        this.calendarRewardsRegister = calendarRewardsRegister;
    }

    
}