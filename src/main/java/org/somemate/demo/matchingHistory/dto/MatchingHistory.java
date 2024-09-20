package org.somemate.demo.matchingHistory.dto;

public class MatchingHistory {
    private int my_idx;
    private int recommended_idx;

    public MatchingHistory(int my_idx, int recommended_idx) {
        this.my_idx = my_idx;
        this.recommended_idx = recommended_idx;
    }

    public int getMy_idx() {
        return my_idx;
    }

    public void setMy_idx(int my_idx) {
        this.my_idx = my_idx;
    }

    public int getRecommended_idx() {
        return recommended_idx;
    }

    public void setRecommended_idx(int recommended_idx) {
        this.recommended_idx = recommended_idx;
    }

    @Override
    public String toString() {
        return "MatchingHistory{" +
                "my_idx=" + my_idx +
                ", recommended_idx=" + recommended_idx +
                '}';
    }
}
