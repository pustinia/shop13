package shop.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import shop.DeliveryserviceApplication;
import shop.domain.DeliveryCanceled;
import shop.domain.DeliveryStarted;

@Entity
@Table(name = "Delivery_table")
@Data
//<<< DDD / Aggregate Root
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;

    private String customerId;

    private String address;

    private String status;

    @PostPersist
    public void onPostPersist() {
        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }

    // 아래로 이동함..
    @PreRemove
    public void onPreRemove(){
        DeliveryCanceled deliveryCanceled = new DeliveryCanceled(this);
        deliveryCanceled.publishAfterCommit();
    }

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = DeliveryserviceApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }

    //<<< Clean Arch / Port Method
    public static void startDelivery(OrderPlaced orderPlaced) {
        //implement business logic here:

        // Example 1:  new item , delivery insert, from orderPlaced object
        Delivery delivery = new Delivery();
        // set 하는 부분을 수정해줌... 20250412 by woongc
        delivery.setOrderId(orderPlaced.getId());
        delivery.setCustomerId(orderPlaced.getCustomerId());
        delivery.setAddress(orderPlaced.getAddress());
        delivery.setStatus("DELIVERY STARTED");
        repository().save(delivery);

        /** Example 2:  finding and process, 일단은 주석처리.. !!!
        repository().findById(orderPlaced.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void cancelDelivery(OrderCanceled orderCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Delivery delivery = new Delivery();
        repository().save(delivery);

        */

        /** Example 2:  finding and process
        

        repository().findById(orderCanceled.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
