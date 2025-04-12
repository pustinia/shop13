package shop.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import shop.InventoryserviceApplication;
import shop.domain.InventoryDecreased;
import shop.domain.InventoryIncreased;

@Entity
@Table(name = "Inventory_table")
@Data
//<<< DDD / Aggregate Root
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String stock;

    @PostPersist
    public void onPostPersist() {
        InventoryDecreased inventoryDecreased = new InventoryDecreased(this);
        inventoryDecreased.publishAfterCommit();

        InventoryIncreased inventoryIncreased = new InventoryIncreased(this);
        inventoryIncreased.publishAfterCommit();
    }

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = InventoryserviceApplication.applicationContext.getBean(
            InventoryRepository.class
        );
        return inventoryRepository;
    }

    //<<< Clean Arch / Port Method
    public static void decreaseInventory(OrderPlaced orderPlaced) {
        //implement business logic here:

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        */

        /** Example 2:  finding and process
        

        repository().findById(orderPlaced.get???()).ifPresent(inventory->{
            
            inventory // do something
            repository().save(inventory);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseInventory(OrderCanceled orderCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        */

        /** Example 2:  finding and process
        

        repository().findById(orderCanceled.get???()).ifPresent(inventory->{
            
            inventory // do something
            repository().save(inventory);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
