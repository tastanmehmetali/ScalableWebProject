package com.waes.assignment.controller;

import java.util.Objects;

public class NumberDetailResponseContract {
    private Long sum;
    private Double avg;

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "NumberDetailResponseContract{" +
                "sum=" + sum +
                ", avg=" + avg +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberDetailResponseContract that = (NumberDetailResponseContract) o;
        return Objects.equals(sum, that.sum) &&
                Objects.equals(avg, that.avg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg);
    }
}
