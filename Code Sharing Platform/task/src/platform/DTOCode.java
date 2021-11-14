package platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class DTOCode {
    public String id;

    public DTOCode(String id) {
        this.id = id;
    }
}