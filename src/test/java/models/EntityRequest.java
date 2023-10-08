package models;

import java.util.List;

public class EntityRequest {
    private AdditionObj addition;
    private List<Integer> important_numbers;
    private String title;
    private Boolean verified;

    public void setAddition(AdditionObj addition) {
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

    public AdditionObj getAddition() {
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