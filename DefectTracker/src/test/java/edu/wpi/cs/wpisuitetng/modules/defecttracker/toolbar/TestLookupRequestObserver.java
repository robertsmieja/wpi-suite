package edu.wpi.cs.wpisuitetng.modules.defecttracker.toolbar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class TestLookupRequestObserver {

    LookupDefectController mockLookupDefectController;

    LookupRequestObserver lookupRequestObserver;

    @Before
    public void setup() {
        mockLookupDefectController = mock(LookupDefectController.class);

        lookupRequestObserver = new LookupRequestObserver(mockLookupDefectController);
    }

    @Test
    public void testLookupRequestObserver() {
        assertNotNull(lookupRequestObserver);
        assertEquals(mockLookupDefectController, lookupRequestObserver.getController());
    }

    //    @Test
    //    public void testResponseSuccess() {
    //        Request mockRequest = mock(Request.class);
    //        ResponseModel mockResponse = mock(ResponseModel.class);
    //
    //        when(mockRequest.getResponse()).thenReturn(mockResponse);
    //        when(mockResponse.getStatusCode()).thenReturn(400);
    //        when(mockResponse.getBody()).thenReturn("defect: { id: ");
    //
    //        lookupRequestObserver.responseSuccess(mockRequest);
    //
    //        // cast observable to a Request
    //        Request request = (Request) iReq;
    //
    //        // get the response from the request
    //        ResponseModel response = request.getResponse();
    //
    //        // check the response code of the request
    //        if (response.getStatusCode() != 200) {
    //            controller.requestFailed();
    //            return;
    //        }
    //
    //        // parse the list of defects received from the core
    //        Defect[] defects = Defect.fromJSONArray(response.getBody());
    //
    //        // make sure that there is actually a defect in the body            
    //        if (defects.length > 0 && defects[0] != null) {
    //            controller.receivedResponse(defects[0]);
    //        } else {
    //            controller.requestFailed();
    //        }
    //    }

    @Test
    public void testResponseError() {
        lookupRequestObserver.responseError(null); //arg ignored

        verify(mockLookupDefectController, times(1)).requestFailed();
    }

    @Test
    public void testFail() {
        lookupRequestObserver.fail(null, null); //args ignored

        verify(mockLookupDefectController, times(1)).requestFailed();
    }

}
