package edu.zstu.examsys.pojo;

public class Condition {
    private Integer offset;
    private Integer max;
    private String order;
    private Boolean desc;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Boolean getDesc() {
        return desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "offset=" + offset +
                ", max=" + max +
                ", order='" + order + '\'' +
                ", desc=" + desc +
                '}';
    }
}
