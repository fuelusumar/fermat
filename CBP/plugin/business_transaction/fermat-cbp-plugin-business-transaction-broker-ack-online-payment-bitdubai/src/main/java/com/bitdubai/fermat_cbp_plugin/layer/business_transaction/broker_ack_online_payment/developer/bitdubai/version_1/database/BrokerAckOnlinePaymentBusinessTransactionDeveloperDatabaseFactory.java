package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.BrokerAckOnlinePaymentBusinessTransactionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 12/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BrokerAckOnlinePaymentBusinessTransactionDeveloperDatabaseFactory implements
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity {
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;
    Database database;
    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public BrokerAckOnlinePaymentBusinessTransactionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }
    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory brokerAckOnlinePaymentBusinessTransactionDatabaseFactory = new BrokerAckOnlinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = brokerAckOnlinePaymentBusinessTransactionDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeBrokerAckOnlinePaymentBusinessTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Broker Ack Online Payment", this.pluginId.toString()));
        return databases;
    }
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();
        /**
         * Table Ack Online Payment columns.
         */
        List<String> onlinePaymentColumns = new ArrayList<String>();

        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TIMESTAMP_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_ADDRESS_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME);
        onlinePaymentColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_CRYPTO_AMOUNT_COLUMN_NAME);
        /**
         * Table Online Payment addition.
         */
        DeveloperDatabaseTable onlinePaymentTable = developerObjectFactory.getNewDeveloperDatabaseTable(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_TABLE_NAME, onlinePaymentColumns);
        tables.add(onlinePaymentTable);

        /**
         * Events Recorder table
         * */
        List<String> eventsRecorderColumns = new ArrayList<String>();

        eventsRecorderColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

        /**
         * Incoming money table
         * */
        List<String> incomingMoneyColumns = new ArrayList<String>();

        incomingMoneyColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_EVENT_ID_COLUMN_NAME);
        incomingMoneyColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        incomingMoneyColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_AMOUNT_COLUMN_NAME);
        incomingMoneyColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_CRYPTO_CURRENCY_COLUMN_NAME);
        incomingMoneyColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_WALLET_PUBLIC_KEY_COLUMN_NAME);
        incomingMoneyColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_SENDER_PUBLIC_KEY_COLUMN_NAME);
        incomingMoneyColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_STATUS_COLUMN_NAME);
        incomingMoneyColumns.add(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable incomingMoneyTable = developerObjectFactory.getNewDeveloperDatabaseTable(BrokerAckOnlinePaymentBusinessTransactionDatabaseConstants.ACK_ONLINE_PAYMENT_INCOMING_MONEY_TABLE_NAME, incomingMoneyColumns);
        tables.add(incomingMoneyTable);

        return tables;
    }
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row: records){
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()){
                    /**
                     * I get each row and save them into a List<String>
                     */
                    developerRow.add(field.getValue());
                }
                /**
                 * I create the Developer Database record
                 */
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            /**
             * return the list of DeveloperRecords for the passed table.
             */
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e){
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}