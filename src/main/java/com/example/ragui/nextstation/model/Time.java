package com.example.ragui.nextstation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohamed on 26/03/2018.
 */

public class Time implements Parcelable
{
    @SerializedName("stopId")
    @Expose
    private String stopId;
    @SerializedName("stopName")
    @Expose
    private String stopName;
    @SerializedName("scheduledArrival")
    @Expose
    private Integer scheduledArrival;
    @SerializedName("scheduledDeparture")
    @Expose
    private Integer scheduledDeparture;
    @SerializedName("realtimeArrival")
    @Expose
    private Integer realtimeArrival;
    @SerializedName("realtimeDeparture")
    @Expose
    private Integer realtimeDeparture;
    @SerializedName("arrivalDelay")
    @Expose
    private Integer arrivalDelay;
    @SerializedName("departureDelay")
    @Expose
    private Integer departureDelay;
    @SerializedName("timepoint")
    @Expose
    private Boolean timepoint;
    @SerializedName("realtime")
    @Expose
    private Boolean realtime;
    @SerializedName("serviceDay")
    @Expose
    private Integer serviceDay;
    @SerializedName("tripId")
    @Expose
    private Integer tripId;

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public Integer getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(Integer scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public Integer getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(Integer scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public Integer getRealtimeArrival() {
        return realtimeArrival;
    }

    public void setRealtimeArrival(Integer realtimeArrival) {
        this.realtimeArrival = realtimeArrival;
    }

    public Integer getRealtimeDeparture() {
        return realtimeDeparture;
    }

    public void setRealtimeDeparture(Integer realtimeDeparture) {
        this.realtimeDeparture = realtimeDeparture;
    }

    public Integer getArrivalDelay() {
        return arrivalDelay;
    }

    public void setArrivalDelay(Integer arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }

    public Integer getDepartureDelay() {
        return departureDelay;
    }

    public void setDepartureDelay(Integer departureDelay) {
        this.departureDelay = departureDelay;
    }

    public Boolean getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(Boolean timepoint) {
        this.timepoint = timepoint;
    }

    public Boolean getRealtime() {
        return realtime;
    }

    public void setRealtime(Boolean realtime) {
        this.realtime = realtime;
    }

    public Integer getServiceDay() {
        return serviceDay;
    }

    public void setServiceDay(Integer serviceDay) {
        this.serviceDay = serviceDay;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Time(Parcel in)
    {
        this.stopId = in.readString();
        this.stopName = in.readString();
        this.scheduledArrival = in.readInt();
        this.scheduledDeparture = in.readInt();
        this.realtimeArrival = in.readInt();
        this.realtimeDeparture = in.readInt();
        this.arrivalDelay = in.readInt();
        this.departureDelay = in.readInt();
        this.serviceDay = in.readInt();
        this.tripId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(stopId);
        parcel.writeString(stopName);
        parcel.writeInt(scheduledArrival);
        parcel.writeInt(scheduledDeparture);
        parcel.writeInt(realtimeArrival);
        parcel.writeInt(realtimeDeparture);
        parcel.writeInt(arrivalDelay);
        parcel.writeInt(departureDelay);
        parcel.writeInt(serviceDay);
        parcel.writeInt(tripId);
    }

    public static final Creator CREATOR =
            new Creator()
            {
                public Time createFromParcel(Parcel in)
                {
                    return new Time(in);
                }

                public Time[] newArray(int size)
                {
                    return new Time[size];
                }
            };
}
