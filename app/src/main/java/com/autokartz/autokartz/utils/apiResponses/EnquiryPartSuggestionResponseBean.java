package com.autokartz.autokartz.utils.apiResponses;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by user on 3/1/2018.
 */

public class EnquiryPartSuggestionResponseBean {


    @SerializedName("id")
    private String id;
    @SerializedName("enquiry_id")
    private String enquiryId;
    @SerializedName("part_id")
    private String partId;
    @SerializedName("requirement_detail")
    private String requirementDetail;
    @SerializedName("agent_remark")
    private String agentRemarks;
    @SerializedName("part_name")
    private String partName;
    @SerializedName("suggestions")
    private ArrayList<SuggestionResponseBean> suggestionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getRequirementDetail() {
        return requirementDetail;
    }

    public void setRequirementDetail(String requirementDetail) {
        this.requirementDetail = requirementDetail;
    }

    public String getAgentRemarks() {
        return agentRemarks;
    }

    public void setAgentRemarks(String agentRemarks) {
        this.agentRemarks = agentRemarks;
    }

    public ArrayList<SuggestionResponseBean> getSuggestionList() {
        return suggestionList;
    }

    public void setSuggestionList(ArrayList<SuggestionResponseBean> suggestionList) {
        this.suggestionList = suggestionList;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

}