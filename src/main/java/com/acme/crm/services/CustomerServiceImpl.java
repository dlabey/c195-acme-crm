package com.acme.crm.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import com.acme.crm.dao.AddressDAO;
import com.acme.crm.dao.CustomerDAO;
import com.acme.crm.entities.CustomerEntity;
import org.apache.logging.log4j.LogManager;

public class CustomerServiceImpl implements CustomerService {

    private static final org.apache.logging.log4j.Logger logger =
            LogManager.getLogger(CustomerServiceImpl.class);

    @Inject
    private DatabaseService dbService;

    @Inject
    private CustomerDAO customerDAO;

    @Inject
    private AddressDAO addressDAO;

    @Override
    public int createCustomer(String name, String address, String address2,
            int cityId, String postalCode, String phone, boolean active,
            String createdBy) throws SQLException {
        int customerId = 0;

        int addressId = this.addressDAO.createAddress(address, address2, cityId,
                postalCode, phone, createdBy);

        customerId = this.customerDAO.createCustomer(name, addressId,
                active, createdBy);

        return customerId;
    }

    @Override
    public boolean editCustomer(int customerId, int addressId, String name,
            String address, String address2, int cityId, String postalCode,
            String phone, boolean active, String updatedBy)
            throws SQLException {
        boolean updated = false;

        try {
            this.dbService.getConnection().setAutoCommit(false);

            PreparedStatement customerPs = this.customerDAO
                    .updateCustomer(customerId, name, active, updatedBy);
            customerPs.executeUpdate();

            PreparedStatement addressPs = this.addressDAO
                    .updateAddress(addressId, address, address2, cityId,
                            postalCode, phone, updatedBy);
            addressPs.executeUpdate();

            this.dbService.getConnection().commit();

            updated = true;
        } catch (SQLException e) {
            this.dbService.getConnection().rollback();

            logger.debug(e.getMessage());
        } finally {
            this.dbService.getConnection().setAutoCommit(true);

            logger.debug(updated);
        }

        return updated;
    }

    @Override
    public boolean deleteCustomer(int customerId, int addressId)
            throws SQLException {
        boolean deleted = false;

        try {
            this.dbService.getConnection().setAutoCommit(false);

            PreparedStatement customerPs = this.customerDAO.deleteCustomer(
                    customerId
            );
            customerPs.executeUpdate();

            PreparedStatement addressPs = this.addressDAO.deleteAddress(
                    addressId
            );
            addressPs.executeUpdate();

            this.dbService.getConnection().commit();

            deleted = true;
        } catch (SQLException e) {
            this.dbService.getConnection().rollback();

            logger.debug(e.getMessage());
        } finally {
            this.dbService.getConnection().setAutoCommit(true);

            logger.debug(deleted);
        }

        return deleted;
    }

    @Override
    public void loadCustomers(TreeTableView customersTable)
            throws SQLException {
        List<CustomerEntity> customers = this.customerDAO.getCustomers();
        List<TreeItem<CustomerEntity>> customerRows = customers.stream()
                .map(customer -> {
                    TreeItem<CustomerEntity> rootItem = new TreeItem<>(customer);
                    TreeItem<CustomerEntity> childItem = new TreeItem<>(customer);

                    rootItem.getChildren().setAll(childItem);

                    return rootItem;
                })
                .collect(Collectors.toList());

        if (customerRows.size() > 0) {
            customersTable.getRoot().getChildren().setAll(
                    FXCollections.observableList(customerRows));
        } else {
            customersTable.getRoot().getChildren().clear();
            customersTable.setPlaceholder(new Label("No customers"));
        }
    }
}
