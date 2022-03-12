package com.ossovita.pronicdemo.model;

import com.google.gson.annotations.SerializedName;

public class Field {

    @SerializedName("fieldPk")
    private long fieldPk;

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("rowNumber")
    private int rowNumber;

    @SerializedName("bold")
    private boolean bold;

    @SerializedName("color")
    private String color;

    public long getFieldPk() {
        return fieldPk;
    }

    public void setFieldPk(long fieldPk) {
        this.fieldPk = fieldPk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldPk=" + fieldPk +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", rowNumber=" + rowNumber +
                ", bold=" + bold +
                ", color='" + color + '\'' +
                '}';
    }
}
