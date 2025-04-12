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

        // Example 2:  finding and process
        // 인벤토리는 example2로 진행.
        // orderPlaced.getProductId 로 변경, 형이 달라짐.. long으로 형변환 고고싱
        repository().findById(Long.valueOf(orderPlaced.getProductId())
            ).ifPresent(inventory->{
            // 여기를 수정함. 아.. stock 값을 string으로 했었네.. 내 실수..
            inventory.setStock(String.valueOf(Integer.valueOf(inventory.getStock()) - orderPlaced.getQty()));
            repository().save(inventory);
            // 내가 찾은 인벤토리의 정보를 이용해서.. 인자 값으로 inventory가 들어가야 함.
            // 수정한 값으로, 퍼블리싱 한다.
            InventoryDecreased inventoryDecreased = new InventoryDecreased(inventory);
            inventoryDecreased.publishAfterCommit();
         });

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

            InventoryIncreased inventoryIncreased = new InventoryIncreased(this);
            inventoryIncreased.publishAfterCommit();
         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
