package com.stats.umer.stats;

import com.stats.umer.stats.Enum;

public class CallbackDataParser<T> {
    private Enum.ServerStatusCodes status;
    private T data;
    private String statusMessage = "";
    private boolean showToUser = false;

    CallbackDataParser()
    {
        status = Enum.ServerStatusCodes.Success;
        statusMessage = "Success";
    }

    CallbackDataParser(Enum.ServerStatusCodes responseStatus, T dataObject)
    {
        status = responseStatus;
        data = dataObject;
        statusMessage = "";
        showToUser = false;
    }

    CallbackDataParser(Enum.ServerStatusCodes responseStatus, String errorMessage, boolean check)
    {
        status = responseStatus;
        data = null;
        statusMessage = errorMessage;
        showToUser = check;
    }

    public Enum.ServerStatusCodes getStatus() {
        return status;
    }

    public void setStatus(Enum.ServerStatusCodes status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isShowToUser() {
        return showToUser;
    }

    public void setShowToUser(boolean showToUser) {
        this.showToUser = showToUser;
    }
}
