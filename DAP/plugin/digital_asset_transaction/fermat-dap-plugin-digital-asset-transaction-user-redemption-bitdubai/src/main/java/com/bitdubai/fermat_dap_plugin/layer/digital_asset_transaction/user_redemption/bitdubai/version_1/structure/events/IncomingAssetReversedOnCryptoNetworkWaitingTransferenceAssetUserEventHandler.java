package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/10/15.
 */
public class IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventHandler implements FermatEventHandler {
    UserRedemptionRecorderService userRedemptionRecorderService;

    public void setUserRedemptionRecorderService(UserRedemptionRecorderService userRedemptionRecorderService) throws CantSetObjectException {
        if(userRedemptionRecorderService==null){
            throw new CantSetObjectException("userRedemptionRecorderService is null");
        }
        this.userRedemptionRecorderService=userRedemptionRecorderService;
    }
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if(this.userRedemptionRecorderService.getStatus()== ServiceStatus.STARTED) {

            try {
                this.userRedemptionRecorderService.incomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEvent((IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent) fermatEvent);
            } catch(CantSaveEventException exception){
                throw new CantSaveEventException(exception,"Handling the IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent", "Check the cause");
            } catch(ClassCastException exception){
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent", "Cannot cast this event");
            } catch(Exception exception){
                throw new CantSaveEventException(exception,"Handling the IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent", "Unexpected exception");
            }

        }else {
            throw new TransactionServiceNotStartedException();
        }

    }
}
