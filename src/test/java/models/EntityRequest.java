package models;

import java.util.List;

public class EntityRequest {
    private Addition addition;
    private List<Integer> important_numbers;
    private String title;
    private Boolean verified;

    public static class Addition {
        private String additional_info;
        private Integer additional_number;

        public Addition(String additional_info, Integer additional_number) {
            this.additional_info = additional_info;
            this.additional_number = additional_number;
        }

        public String getAdditional_info() {
            return additional_info;
        }

        public Integer getAdditional_number() {
            return additional_number;
        }
    }

    public void setAddition(Addition addition) {
        this.addition = addition;
    }

    public void setImportant_numbers(List<Integer> importantNumbers) {
        this.important_numbers = importantNumbers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Addition getAddition() {
        return addition;
    }

    public List<Integer> getImportant_numbers() {
        return important_numbers;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getVerified() {
        return verified;
    }
}