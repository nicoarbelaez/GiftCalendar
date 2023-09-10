package gc.nicoarbelaez.models;

import java.util.List;

public class PlayerModel {
    private String uuid;
    private List<Integer> claimedDays;

    public PlayerModel(String uuid, List<Integer> claimedDays) {
        this.uuid = uuid;
        this.claimedDays = claimedDays;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Integer> getClaimedDays() {
        return claimedDays;
    }

    public void setClaimedDays(List<Integer> claimedDays) {
        this.claimedDays = claimedDays;
    }
}