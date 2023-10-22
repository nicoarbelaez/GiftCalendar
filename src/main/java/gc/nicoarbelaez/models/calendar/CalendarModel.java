package gc.nicoarbelaez.models.calendar;

import java.util.List;
import java.util.Map;

public class CalendarModel {
    private String calendarName;
    private CalendarConfigModel calendarConfig;
    private List<RewardModel> rewardList;
    private int[] rewardSlotsPerPage;
    private int pages;
    private Map<String, ItemModel> itemMap;
    private List<ItemModel> customItemList;

    public CalendarModel(String calendarName, CalendarConfigModel calendarConfig, List<RewardModel> rewardList,
            int[] rewardSlotsPerPage, Map<String, ItemModel> itemMap, List<ItemModel> customItemList) {
        this.calendarName = calendarName;
        this.calendarConfig = calendarConfig;
        this.rewardList = rewardList;
        this.rewardSlotsPerPage = rewardSlotsPerPage;
        this.itemMap = itemMap;
        this.customItemList = customItemList;
        this.pages = (int) Math.ceil(calendarConfig.getDays() / (float) rewardSlotsPerPage.length);
    }

    public void sortCustomItemByPriority() {
        customItemList.sort((item1, item2) -> {
            if (item1.getPriority() == 0) {
                return -1;
            }
            if (item2.getPriority() == 0) {
                return 1;
            }
            return Integer.compare(item1.getPriority(), item2.getPriority());
        });

        for (ItemModel itemModel : customItemList) {
            System.out.print("[CalendarModel] (itemModelList)" + itemModel.getId() + " " + itemModel.getPriority()); // TODO:
                                                                                                                      // Eliminar
        }
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public CalendarConfigModel getCalendarConfig() {
        return calendarConfig;
    }

    public void setCalendarConfig(CalendarConfigModel calendarConfig) {
        this.calendarConfig = calendarConfig;
    }

    public List<RewardModel> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<RewardModel> rewardList) {
        this.rewardList = rewardList;
    }

    public int[] getRewardSlotsPerPage() {
        return rewardSlotsPerPage;
    }

    public void setRewardSlotsPerPage(int[] rewardSlotsPerPage) {
        this.rewardSlotsPerPage = rewardSlotsPerPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Map<String, ItemModel> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, ItemModel> itemMap) {
        this.itemMap = itemMap;
    }

    public List<ItemModel> getCustomItemList() {
        return customItemList;
    }

    public void setCustomItemList(List<ItemModel> customItemList) {
        this.customItemList = customItemList;
    }

}
