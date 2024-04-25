package com.example.dialogue.helpers;

import android.os.AsyncTask;
import android.util.Log;
import com.example.dialogue.interfaces.BotReply;
import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;

public class SendMessageInBg extends AsyncTask<Void, Void, DetectIntentResponse> {


  private SessionName session;
  private SessionsClient sessionsClient;
  private QueryInput queryInput;
  private String TAG = "async";

  private BotReply botReply;

  public SendMessageInBg(BotReply botReply, SessionName session, SessionsClient sessionsClient,
      QueryInput queryInput) {
    this.botReply = botReply;
    this.session = session;
    this.sessionsClient = sessionsClient;
    this.queryInput = queryInput;
  }

  @Override
  protected DetectIntentResponse doInBackground(Void... voids) {
    try {
      DetectIntentRequest detectIntentRequest =
          DetectIntentRequest.newBuilder()
              .setSession(session.toString())
              .setQueryInput(queryInput)
              .build();
      return sessionsClient.detectIntent(detectIntentRequest);
    } catch (Exception e) {
      Log.e(TAG, "Error in doInBackground: ", e);
      e.printStackTrace();
      return createErrorResponse("An error occurred while processing your request.");
    }
  }

  /*private DetectIntentResponse createErrorResponse() {
    DetectIntentResponse.Builder responseBuilder = DetectIntentResponse.newBuilder();
    QueryResult queryResult = QueryResult.newBuilder().build();
    return responseBuilder.setQueryResult(queryResult).build();
  }*/

  private DetectIntentResponse createErrorResponse(String errorMessage) {
    DetectIntentResponse.Builder responseBuilder = DetectIntentResponse.newBuilder();
    QueryResult queryResult = QueryResult.newBuilder()
            .setFulfillmentText(errorMessage)
            .build();
    return responseBuilder.setQueryResult(queryResult).build();
  }
  @Override
  protected void onPostExecute(DetectIntentResponse response) {
    //handle return response here
    botReply.callback(response);
  }
}