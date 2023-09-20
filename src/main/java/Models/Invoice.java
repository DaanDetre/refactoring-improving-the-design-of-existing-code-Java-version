package Models;

import java.util.List;

public class Invoice {

    String customer;

     List<Performance> performances;

    public Invoice(List<Performance> performances, String customer){
        this.customer = customer;
        this.performances = performances;
    }

    public Invoice(){

    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }
}
