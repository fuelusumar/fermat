package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class BrokerSubmitOnlineMerchandiseBusinessTransactionDao {
    
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public BrokerSubmitOnlineMerchandiseBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database             = database            ;
    }

    public void initialize() throws CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Generic Exception.");
        }
    }

    /**
     * Returns the Database
     *
     * @return Database
     */
    private Database getDataBase() {
        return database;
    }

    /**
     * Returns the Ack Online Payment DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseSubmitTable() {
        return getDataBase().getTable(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TABLE_NAME);
    }

    /**
     * Returns the Submit Online Merchandise Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * This method save an incoming new event in database.
     * @param eventType
     * @param eventSource
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            UUID eventRecordID = UUID.randomUUID();
            saveNewEvent(eventType, eventSource, eventRecordID.toString());

        } catch(Exception exception){
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method save an incoming new event in database. You can set the event Id with this method
     * @param eventType
     * @param eventSource
     * @param eventId
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource, String eventId) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            long unixTime = System.currentTimeMillis();
            eventRecord.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Submit Online Merchandise database");
        } catch(Exception exception){
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns the actual contract transaction status
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{

            String stringContractTransactionStatus=getValue(
                    contractHash,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);
            return ContractTransactionStatus.getByCode(stringContractTransactionStatus);
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    e,
                    "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");
        }
    }

    /**
     * This method returns a String value from parameters in database.
     * @param key
     * @param keyColumn
     * @param valueColumn
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private String getValue(String key,
                            String keyColumn,
                            String valueColumn)
            throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseSubmitTable();
            databaseTable.addStringFilter(
                    keyColumn,
                    key,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            String value=records
                    .get(0)
                    .getStringValue(valueColumn);
            return value;
        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    /**
     * This method check the database record result.
     * @param records
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws
            UnexpectedResultReturnedFromDatabaseException {
        /**
         * Represents the maximum number of records in <code>records</code>
         * I'm gonna set this number in 1 for now, because I want to check the records object has
         * one only result.
         */
        int VALID_RESULTS_NUMBER=1;
        int recordsSize;
        if(records.isEmpty()){
            return;
        }
        recordsSize=records.size();
        if(recordsSize>VALID_RESULTS_NUMBER){
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted "+VALID_RESULTS_NUMBER+", but I got "+recordsSize);
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractSale in crypto broker side, only for backup
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale)
            throws CantInsertRecordException {

        DatabaseTable databaseTable=getDatabaseSubmitTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord= buildDatabaseTableRecord(
                databaseTableRecord,
                customerBrokerContractSale
        );
        databaseTable.insertRecord(databaseTableRecord);
    }

    /**
     * This method persists a basic record in database
     * @param customerBrokerContractSale
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(
            CustomerBrokerContractSale customerBrokerContractSale,
            String brokerCryptoAddress,
            String walletPublicKey,
            long cryptoAmount)
            throws CantInsertRecordException {

        DatabaseTable databaseTable=getDatabaseSubmitTable();
        DatabaseTableRecord databaseTableRecord=databaseTable.getEmptyRecord();
        databaseTableRecord= buildDatabaseTableRecord(
                databaseTableRecord,
                customerBrokerContractSale,
                brokerCryptoAddress,
                walletPublicKey,
                cryptoAmount
        );
        databaseTable.insertRecord(databaseTableRecord);
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractPurchase object.
     * This record is not complete, is missing the transaction hash,  and the crypto status,
     * this values will after sending the crypto amount, also the timestamp is set at this moment.
     * @param record
     * @param customerBrokerContractSale
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale,
            String brokerCryptoAddress,
            String walletPublicKey,
            long cryptoAmount) {

        UUID transactionId=UUID.randomUUID();
        record.setUUIDValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ONLINE_DE_STOCK.getCode());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME,
                brokerCryptoAddress);
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_WALLET_PUBLIC_KEY_COLUMN_NAME,
                walletPublicKey);
        record.setLongValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME,
                cryptoAmount);
        return record;
    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     * @param record
     * @param customerBrokerContractSale
     * @return
     */
    private DatabaseTableRecord buildDatabaseTableRecord(
            DatabaseTableRecord record,
            CustomerBrokerContractSale customerBrokerContractSale){
        UUID transactionId=UUID.randomUUID();
        record.setUUIDValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME,
                transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                customerBrokerContractSale.getContractId());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyCustomer());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME,
                customerBrokerContractSale.getPublicKeyBroker());
        record.setStringValue(
                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                ContractTransactionStatus.PENDING_ONLINE_DE_STOCK.getCode());

        return record;
    }

}
