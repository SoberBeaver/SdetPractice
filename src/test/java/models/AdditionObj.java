package models;

public class AdditionObj {
    private String additional_info;
    private Integer additional_number;

    public AdditionObj(String additional_info, Integer additional_number) {
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