<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: 'Orderservice' }" />
        <VerticalNavLink
            :item="{
                title: 'Order',
                to: '/orders',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Deliveryservice' }" />
        <VerticalNavLink
            :item="{
                title: 'Delivery',
                to: '/deliveries',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Inventoryservice' }" />
        <VerticalNavLink
            :item="{
                title: 'Inventory',
                to: '/inventories',
            }"
        />
    </ul>
</template>
