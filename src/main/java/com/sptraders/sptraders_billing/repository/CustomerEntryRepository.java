package com.sptraders.sptraders_billing.repository;

import com.sptraders.sptraders_billing.entity.CustomerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerEntryRepository extends JpaRepository<CustomerEntry, Long> {

    List<CustomerEntry> findByCustomer(String name);

    List<CustomerEntry> findByDateBetween(Date fromDate, Date toDate);

    List<CustomerEntry> findByCustomerAndDateBetween(String name, Date fromDate, Date toDate);
}
