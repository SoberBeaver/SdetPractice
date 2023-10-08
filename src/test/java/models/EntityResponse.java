package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class EntityResponse {
    public int id;
    public String title;
    public boolean verified;
    public List<Integer> important_numbers;
    public Addition addition;

    public int getAdditionId() {
        return addition.id;
    }
    public String getAdditionalInfo() {
        return addition.additional_info;
    }
    public int getAdditionalNumber() {
        return addition.additional_number;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getVerified() {
        return verified;
    }

    public List<Integer> getImportant_numbers() {
        return important_numbers;
    }

    public class Addition {
        public Addition() {}
        public int id;
        public String additional_info;
        public int additional_number;
    }
}

