package org.fasttrackit.pizzashop.transfer;


public class GetPizzasRequest {

    private String partialName;
    //allowing null by using wrapper class
    private Integer minQuantity;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    @Override
    public String toString() {
        return "GetPizzasRequest{" +
                "partialName='" + partialName + '\'' +
                ", minQuantity=" + minQuantity +
                '}';
    }
}
