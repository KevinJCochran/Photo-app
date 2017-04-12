package model;

import java.io.Serializable;

public class Tag implements Serializable {

    private String type;
    private String value;

    public Tag(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (!type.equals(tag.type)) return false;
        return value.equals(tag.value);
    }

    @Override
    public String toString() {
        return (type + " : " + value);
    }
}
