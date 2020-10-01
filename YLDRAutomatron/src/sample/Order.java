package sample;

import java.util.Date;

public class Order {
    Date startDate;
    Date endDate;
    Date download;
    Product product [] = new Product[10];

    public Order() {
    }

    public Order(Date startDate, Date endDate, Date download) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.download = download;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDownload() {
        return download;
    }

    public void setDownload(Date download) {
        this.download = download;
    }
}
