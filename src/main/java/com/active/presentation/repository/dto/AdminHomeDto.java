package com.active.presentation.repository.dto;

import com.active.presentation.domain.Answer;
import com.active.presentation.domain.PresentationDashboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/27/14
 */
public class AdminHomeDto {

    private Long pollTotal;
    private Long pollTotalDiff;

    private Long oxTotal;
    private Long oxTotalDiff;

    private Long multipleTotal;
    private Long multipleTotalDiff;

    private Long qnaTotal;
    private Long qnaTotalDiff;

    private Long answerTotal;
    private Long answerTotalDiff;
    private Long answerToday;

    private Long audienceTotal;
    private Long audienceTotalDiff;

    private List<DashboardTop10Dto> top10 = new ArrayList<DashboardTop10Dto>();

    private List<PresentationDashboard> latest = new ArrayList<PresentationDashboard>();

    private List<AnswerResultDto> qna = new ArrayList<AnswerResultDto>();

    private List<AnswerTransactionDto> transaction = new ArrayList<AnswerTransactionDto>();

    public AdminHomeDto() {}

    public Long getPollTotal() {
        return pollTotal;
    }

    public void setPollTotal(Long pollTotal) {
        this.pollTotal = pollTotal;
    }

    public Long getPollTotalDiff() {
        return pollTotalDiff;
    }

    public void setPollTotalDiff(Long pollTotalDiff) {
        this.pollTotalDiff = pollTotalDiff;
    }

    public Long getOxTotal() {
        return oxTotal;
    }

    public void setOxTotal(Long oxTotal) {
        this.oxTotal = oxTotal;
    }

    public Long getOxTotalDiff() {
        return oxTotalDiff;
    }

    public void setOxTotalDiff(Long oxTotalDiff) {
        this.oxTotalDiff = oxTotalDiff;
    }

    public Long getMultipleTotal() {
        return multipleTotal;
    }

    public void setMultipleTotal(Long multipleTotal) {
        this.multipleTotal = multipleTotal;
    }

    public Long getMultipleTotalDiff() {
        return multipleTotalDiff;
    }

    public void setMultipleTotalDiff(Long multipleTotalDiff) {
        this.multipleTotalDiff = multipleTotalDiff;
    }

    public Long getQnaTotal() {
        return qnaTotal;
    }

    public void setQnaTotal(Long qnaTotal) {
        this.qnaTotal = qnaTotal;
    }

    public Long getQnaTotalDiff() {
        return qnaTotalDiff;
    }

    public void setQnaTotalDiff(Long qnaTotalDiff) {
        this.qnaTotalDiff = qnaTotalDiff;
    }

    public Long getAnswerTotal() {
        return answerTotal;
    }

    public void setAnswerTotal(Long answerTotal) {
        this.answerTotal = answerTotal;
    }

    public Long getAnswerTotalDiff() {
        return answerTotalDiff;
    }

    public void setAnswerTotalDiff(Long answerTotalDiff) {
        this.answerTotalDiff = answerTotalDiff;
    }

    public Long getAnswerToday() {
        return answerToday;
    }

    public void setAnswerToday(Long answerToday) {
        this.answerToday = answerToday;
    }

    public Long getAudienceTotal() {
        return audienceTotal;
    }

    public void setAudienceTotal(Long audienceTotal) {
        this.audienceTotal = audienceTotal;
    }

    public Long getAudienceTotalDiff() {
        return audienceTotalDiff;
    }

    public void setAudienceTotalDiff(Long audienceTotalDiff) {
        this.audienceTotalDiff = audienceTotalDiff;
    }

    public List<DashboardTop10Dto> getTop10() {
        return top10;
    }

    public void setTop10(List<DashboardTop10Dto> top10) {
        this.top10 = top10;
    }

    public List<PresentationDashboard> getLatest() {
        return latest;
    }

    public void setLatest(List<PresentationDashboard> latest) {
        this.latest = latest;
    }

    public List<AnswerResultDto> getQna() {
        return qna;
    }

    public void setQna(List<AnswerResultDto> qna) {
        this.qna = qna;
    }

    public List<AnswerTransactionDto> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<AnswerTransactionDto> transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "AdminHomeDto{" +
                "pollTotal=" + pollTotal +
                ", pollTotalDiff=" + pollTotalDiff +
                ", oxTotal=" + oxTotal +
                ", oxTotalDiff=" + oxTotalDiff +
                ", multipleTotal=" + multipleTotal +
                ", multipleTotalDiff=" + multipleTotalDiff +
                ", qnaTotal=" + qnaTotal +
                ", qnaTotalDiff=" + qnaTotalDiff +
                ", answerTotal=" + answerTotal +
                ", answerTotalDiff=" + answerTotalDiff +
                ", answerToday=" + answerToday +
                ", audienceTotal=" + audienceTotal +
                ", audienceTotalDiff=" + audienceTotalDiff +
                ", top10" + top10 +
                ", latest.size=" + latest.size() +
                ", qna.size=" + qna.size() +
                '}';
    }
}
