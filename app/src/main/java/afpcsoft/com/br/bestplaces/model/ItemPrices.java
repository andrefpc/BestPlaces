package afpcsoft.com.br.bestplaces.model;

/**
 * Created by AndréFelipe on 16/05/2015.
 */
public class ItemPrices {
    private String item;
    private String description;
    private String price;

    public ItemPrices() {
    }

    public ItemPrices(String item, String description, String price) {

        this.item = item;
        this.description = description;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
