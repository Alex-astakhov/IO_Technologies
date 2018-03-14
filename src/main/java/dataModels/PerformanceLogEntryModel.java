package dataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerformanceLogEntryModel {
    @SerializedName("message")
    @Expose
    public Message message;

    public class Message {
        @SerializedName("params")
        @Expose
        public Params params;

    }

    public class Params {
        @SerializedName("request")
        @Expose
        public Request request;
    }

    public class Request {
        @SerializedName("method")
        @Expose
        public String method;
        @SerializedName("url")
        @Expose
        public String url;
    }
}
